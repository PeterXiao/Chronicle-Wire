/*
 * Copyright 2016-2022 chronicle.software
 *
 *       https://chronicle.software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.wire.channel.impl;

import net.openhft.affinity.AffinityThreadFactory;
import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.core.io.Closeable;
import net.openhft.chronicle.threads.NamedThreadFactory;
import net.openhft.chronicle.threads.Pauser;
import net.openhft.chronicle.wire.DocumentContext;
import net.openhft.chronicle.wire.UnrecoverableTimeoutException;
import net.openhft.chronicle.wire.Wire;
import net.openhft.chronicle.wire.WireOut;
import net.openhft.chronicle.wire.channel.EventPoller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static net.openhft.chronicle.wire.channel.impl.TCPChronicleChannel.validateHeader;

public class BufferedChronicleChannel extends DelegateChronicleChannel {
    static final long LINGER_NS = (long) (Double.parseDouble(System.getProperty("wire.lingerUS", "20")) * 1e3);

    static {
        if (LINGER_NS != 20_000)
            Jvm.perf().on(BufferedChronicleChannel.class, "wire.lingerUS: " + LINGER_NS / 1e3);
    }

    private final Pauser pauser;
    private final WireExchanger exchanger = new WireExchanger();
    private final ExecutorService bgWriter;
    private volatile EventPoller eventPoller;

    public BufferedChronicleChannel(TCPChronicleChannel channel, Pauser pauser) {
        super(channel);
        this.pauser = pauser;

        String desc = channel.connectionCfg().initiator() ? "init" : "accp";
        final String writer = desc + "~writer";
        final ThreadFactory factory = pauser.isBusy()
                ? new AffinityThreadFactory(writer, true)
                : new NamedThreadFactory(writer, true);
        bgWriter = Executors.newSingleThreadExecutor(factory);
        bgWriter.submit(this::bgWrite);
    }

    @Override
    public EventPoller eventPoller() {
        return eventPoller;
    }

    @Override
    public BufferedChronicleChannel eventPoller(EventPoller eventPoller) {
        this.eventPoller = eventPoller;
        return this;
    }

    private void bgWrite() {
        try {
            final TCPChronicleChannel channel = (TCPChronicleChannel) this.channel;
            while (!isClosing()) {
                long start = System.nanoTime();
                channel.checkConnected();
                final Wire wire = exchanger.acquireConsumer();
                if (wire.bytes().isEmpty()) {
                    final EventPoller eventPoller = this.eventPoller();
                    if (eventPoller == null || !eventPoller.onPoll(this)) {
                        pauser.pause();
                    }
                    exchanger.releaseConsumer();
                    continue;
                }
                assert validateHeader(wire.bytes().peekVolatileInt());
                // System.out.println("Writing - " + Wires.fromSizePrefixedBlobs(wire));
                pauser.reset();
//                long size = wire.bytes().readRemaining();
                channel.flushOut(wire);
                exchanger.releaseConsumer();
                while (System.nanoTime() < start + LINGER_NS) {
                    pauser.pause();
                }
            }
        } catch (Throwable t) {
            if (!isClosing())
                Jvm.warn().on(getClass(), "bgWriter died", t);
        } finally {
            bgWriter.shutdown();
            Closeable.closeQuietly(eventPoller());
        }
    }

    @Override
    public DocumentContext writingDocument(boolean metaData) throws UnrecoverableTimeoutException {
        return exchanger.writingDocument(metaData);
    }

    @Override
    public DocumentContext acquireWritingDocument(boolean metaData) throws UnrecoverableTimeoutException {
        return exchanger.acquireWritingDocument(metaData);
    }

    @Override
    public WireOut acquireProducer() {
        return exchanger.acquireProducer();
    }

    @Override
    public void releaseProducer() {
        exchanger.releaseProducer();
    }

    @Override
    public void close() {
        super.close();
        exchanger.close();
    }
}
