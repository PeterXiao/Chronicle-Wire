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

package net.openhft.chronicle.wire;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.HexDumpBytes;
import net.openhft.chronicle.bytes.MethodReader;
import net.openhft.chronicle.core.io.IORuntimeException;
import org.junit.Test;

import static net.openhft.chronicle.bytes.MethodReader.MESSAGE_HISTORY_METHOD_ID;
import static org.junit.Assert.*;

public class MessageHistoryTest extends WireTestCommon {

    @Test
    public void checkHistoryGetClear() {
        MessageHistory mg = MessageHistory.get();
        assertNotNull(mg);
        MessageHistory.set(null);
        MessageHistory mg2 = MessageHistory.get();
        assertNotNull(mg2);
        assertNotSame(mg, mg2);
    }

    @Test
    public void checkDeepCopy() {
        VanillaMessageHistory history = new VanillaMessageHistory();
        initExampleMessageHistory(history);
        VanillaMessageHistory history2 = history.deepCopy();
        assertEquals(history, history2);
    }

    @Test
    public void checkHistoryMaxSizeException() {
        VanillaMessageHistory container1 = new VanillaMessageHistory();
        container1.addSourceDetails(true);
        VanillaMessageHistory container2 = new VanillaMessageHistory();
        container2.addSourceDetails(true);
        for (int i = 0; i < VanillaMessageHistory.MESSAGE_HISTORY_LENGTH / 2; i++) {
            Wires.copyTo(container1, container2);
            Wires.copyTo(container2, container1);
        }
        try {
            Wires.copyTo(container1, container2);
            fail();
        } catch (IllegalStateException e) {
            // all good
        }
    }

    @Test
    public void checkSerialiseBytes() {

        VanillaMessageHistory history = new SetTimeMessageHistory();
        initExampleMessageHistory(history);
        BinaryWire bw = new BinaryWire(Bytes.elasticHeapByteBuffer());
        history.writeMarshallable(bw);
        VanillaMessageHistory history2 = new SetTimeMessageHistory();
        history2.historyWallClock(false);
        history2.readMarshallable(bw);
        assertEquals("VanillaMessageHistory { " +
                "sources: [1=0xff,2=0xfff], " +
                "timings: [1000000000000000000,1000000000000010000,120962203520100], " +
                "addSourceDetails=false }", history2.toString());

        bw.bytes().readPosition(0);
        history2.addSourceDetails(true);
        SourceContext sc = new SourceContext() {
            @Override
            public int sourceId() {
                return 3;
            }

            @Override
            public long index() throws IORuntimeException {
                return 0xffff;
            }
        };
        bw.parent(sc);
        history2.readMarshallable(bw);
        assertEquals("VanillaMessageHistory { " +
                "sources: [1=0xff,2=0xfff,3=0xffff], " +
                "timings: [1000000000000000000,1000000000000010000,120962203520100,120962203520100], " +
                "addSourceDetails=true }", history2.toString());
    }

    @Test
    public void checkToString() {
        try {
            System.setProperty("history.wall.clock", "true");
            VanillaMessageHistory history = new SetTimeMessageHistory();
            history.addSourceDetails(true);
            history.historyWallClock(true);
            initExampleMessageHistory(history);
            assertEquals(2, history.sources());
            assertEquals(2, history.timings());

            BinaryWire bw = new BinaryWire(new HexDumpBytes());
            bw.writeEventName(MethodReader.HISTORY).marshallable(history);
            assertEquals("" +
                            "b9 07 68 69 73 74 6f 72 79                      # history: (event)\n" +
                            "81 4b 00                                        # SetTimeMessageHistory\n" +
                            "c7 73 6f 75 72 63 65 73                         # sources:\n" +
                            "82 16 00 00 00                                  # sequence\n" +
                            "                                                # source id & index\n" +
                            "a1 01 af ff 00 00 00 00 00 00 00                # 1\n" +
                            "                                                # source id & index\n" +
                            "a1 02 af ff 0f 00 00 00 00 00 00                # 2\n" +
                            "c7 74 69 6d 69 6e 67 73                         # timings:\n" +
                            "82 1b 00 00 00                                  # sequence\n" +
                            "                                                # timing in nanos\n" +
                            "a7 00 00 64 a7 b3 b6 e0 0d                      # 1000000000000000000\n" +
                            "                                                # timing in nanos\n" +
                            "a7 10 27 64 a7 b3 b6 e0 0d                      # 1000000000000010000\n" +
                            "a7 64 0c 2c b5 03 6e 00 00                      # 120962203520100\n",
                    bw.bytes().toHexString());
            bw.bytes().releaseLast();

            assertEquals("VanillaMessageHistory { sources: [1=0xff,2=0xfff], timings: [ 2001-09-09T01:46:40, 2001-09-09T01:46:40.00001 ], addSourceDetails=true }",
                    history.toString());
            assertEquals(2, history.sources());
            assertEquals(2, history.timings());

            BinaryWire bw2 = new BinaryWire(new HexDumpBytes());
            history.useBytesMarshallable(true);
            bw2.writeEventName(MethodReader.HISTORY).marshallable(history);
            assertEquals("" +
                            "b9 07 68 69 73 74 6f 72 79                      # history: (event)\n" +
                            "81 33 00 86                                     # SetTimeMessageHistory\n" +
                            "02 01 00 00 00 02 00 00 00 ff 00 00 00 00 00 00 # sources\n" +
                            "00 ff 0f 00 00 00 00 00 00 03 00 00 64 a7 b3 b6 # timings\n" +
                            "e0 0d 10 27 64 a7 b3 b6 e0 0d 64 0c 2c b5 03 6e\n" +
                            "00 00\n",
                    bw2.bytes().toHexString());
            bw2.bytes().releaseLast();


            // check direct and on heap memory serialize the same.
            Wire wire1 = new BinaryWire(Bytes.allocateElasticOnHeap());
            Wire wire2 = new BinaryWire(Bytes.allocateElasticDirect());
            history.writeMarshallable(wire1);
            history.writeMarshallable(wire2);
            String hexString1 = wire1.bytes().toHexString();
            String hexString2 = wire2.bytes().toHexString();
            assertEquals(hexString1, hexString2);
            VanillaMessageHistory mh1 = new SetTimeMessageHistory();
            mh1.historyWallClock(true);
            mh1.addSourceDetails(false);
            mh1.readMarshallable(wire1);
            assertTrue(mh1.toString().startsWith("VanillaMessageHistory { sources: [1=0xff,2=0xfff], timings: [ 2001-09-09T01:46:40, 2001-09-09T01:46:40.00001,"));
            VanillaMessageHistory mh2 = new SetTimeMessageHistory();
            mh2.historyWallClock(true);
            mh2.addSourceDetails(false);
            mh2.readMarshallable(wire2);
            assertTrue(mh2.toString().startsWith("VanillaMessageHistory { sources: [1=0xff,2=0xfff], timings: [ 2001-09-09T01:46:40, 2001-09-09T01:46:40.00001,"));
        } finally {
            System.clearProperty("history.wall.clock");
        }
    }

    @Test
    public void testReadMarshallable() {
        try {
            System.setProperty("history.wall.clock", "true");
            SetTimeMessageHistory vmh = new SetTimeMessageHistory();
            vmh.addSource(1, 2);
            vmh.addTiming(1111);
            vmh.addTiming(2222);

            HexDumpBytes bytes = new HexDumpBytes();
            Wire wire = new BinaryWire(bytes);
            vmh.useBytesMarshallable(false);
            wire.writeEventName(MethodReader.HISTORY).object(SetTimeMessageHistory.class, vmh);

            vmh.nanoTime = 120962203520100L;
            vmh.useBytesMarshallable(true);
            wire.writeEventId(MESSAGE_HISTORY_METHOD_ID).object(SetTimeMessageHistory.class, vmh);

            assertEquals("b9 07 68 69 73 74 6f 72 79                      # history: (event)\n" +
                            "81 34 00                                        # SetTimeMessageHistory\n" +
                            "c7 73 6f 75 72 63 65 73                         # sources:\n" +
                            "82 0b 00 00 00                                  # sequence\n" +
                            "                                                # source id & index\n" +
                            "a1 01 af 02 00 00 00 00 00 00 00                # 1\n" +
                            "c7 74 69 6d 69 6e 67 73                         # timings:\n" +
                            "82 0f 00 00 00                                  # sequence\n" +
                            "                                                # timing in nanos\n" +
                            "a5 57 04                                        # 1111\n" +
                            "                                                # timing in nanos\n" +
                            "a5 ae 08                                        # 2222\n" +
                            "a7 64 0c 2c b5 03 6e 00 00 ba 80 00             # 120962203520100\n" +
                            "81 27 00 86                                     # SetTimeMessageHistory\n" +
                            "01 01 00 00 00 02 00 00 00 00 00 00 00          # sources\n" +
                            "03 57 04 00 00 00 00 00 00 ae 08 00 00 00 00 00 # timings\n" +
                            "00 64 0c 2c b5 03 6e 00 00\n",
                    bytes.toHexString());
            vmh.addTiming(120962203520100L);

            VanillaMessageHistory vmh2 = new VanillaMessageHistory();
            wire.read().object(vmh2, VanillaMessageHistory.class);
            assertEquals(vmh.toString(), vmh2.toString());

            VanillaMessageHistory vmh3 = new VanillaMessageHistory();
            wire.read().object(vmh3, VanillaMessageHistory.class);
            assertEquals(vmh.toString(), vmh3.toString());
        } finally {
            System.clearProperty("history.wall.clock");
        }
    }

    @Test
    public void testWriteHistory() {
        try {
            final SetTimeMessageHistory history = new SetTimeMessageHistory();
            initExampleMessageHistory(history);
            MessageHistory.set(history);

            final Bytes<?> bytes = Bytes.allocateElasticOnHeap();
            final Wire wire = new BinaryWire(bytes);
            try (DocumentContext dc = wire.writingDocument()) {
                MessageHistory.writeHistory(dc);
            }

            assertEquals("00000000 57 00 00 00 b9 07 68 69  73 74 6f 72 79 81 4b 00 W·····hi story·K·",
                    bytes.toHexString().split("\n")[0]);

            System.setProperty("history.as.method_id", "true");
            final SetTimeMessageHistory history2 = new SetTimeMessageHistory();
            history2.historyMethodId(true);
            initExampleMessageHistory(history2);
            MessageHistory.set(history2);

            wire.reset();
            try (DocumentContext dc = wire.writingDocument()) {
                MessageHistory.writeHistory(dc);
            }

            assertEquals("00000000 51 00 00 00 ba 80 00 81  4b 00 c7 73 6f 75 72 63 Q······· K··sourc",
                    bytes.toHexString().split("\n")[0]);

        } finally {
            System.clearProperty("history.as.method_id");
            MessageHistory.set(null);
        }
    }

    private static void initExampleMessageHistory(VanillaMessageHistory history) {
        history.addSource(1, 0xff);
        history.addSource(2, 0xfff);
        history.addTiming(1_000_000_000_000_000_000L);
        history.addTiming(1_000_000_000_000_010_000L);
    }

    static class SetTimeMessageHistory extends VanillaMessageHistory {
        long nanoTime = 120962203520100L;

        @Override
        protected long nanoTime() {
            return nanoTime;
        }
    }
}
