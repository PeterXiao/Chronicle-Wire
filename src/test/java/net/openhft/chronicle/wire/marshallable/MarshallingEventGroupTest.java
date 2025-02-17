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

package net.openhft.chronicle.wire.marshallable;

import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.threads.EventGroup;
import net.openhft.chronicle.threads.EventGroupBuilder;
import net.openhft.chronicle.threads.Pauser;
import net.openhft.chronicle.threads.PauserMode;
import net.openhft.chronicle.wire.WireTestCommon;
import net.openhft.chronicle.wire.WireType;
import org.junit.Test;

import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarshallingEventGroupTest extends WireTestCommon {
    @Test
    public void test() {
        // produced longer timeout in debug
        assumeFalse(Jvm.isDebug());
        try (final EventGroup eg =
                     EventGroupBuilder.builder()
                             .withName("test")
                             .withBinding("none")
                             .withPauser(Pauser.sleepy())
                             .withConcurrentPauserSupplier(PauserMode.sleepy)
                             .withBlockingPauserSupplier(PauserMode.sleepy)
                             .withConcurrentThreadsNum(1)
                             .build()) {
            final String actual = WireType.TEXT.asString(eg);

            String oldExpected = "" +
                    "!net.openhft.chronicle.threads.EventGroup {\n" +
                    "  referenceId: 0,\n" +
                    "  lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "  name: test,\n" +
                    "  counter: 0,\n" +
                    "  monitor: !net.openhft.chronicle.threads.MonitorEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/~monitortest/event~loop~monitor,\n" +
                    "    handlers: [\n" +
                    "      !net.openhft.chronicle.threads.MonitorEventLoop$IdempotentLoopStartedEventHandler { referenceId: 0, handler: \"PauserMonitor<test/core-pauser>\", loopStarted: false }\n" +
                    "    ],\n" +
                    "    pauser: !net.openhft.chronicle.threads.MilliPauser { pausing: false, pauseTimeMS: 10, timePaused: 0, countPaused: 0, pauseUntilMS: 0 }\n" +
                    "  },\n" +
                    "  core: !net.openhft.chronicle.threads.VanillaEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/core-event-loop,\n" +
                    "    mediumHandlers: [    ],\n" +
                    "    newHandler: !!null \"\",\n" +
                    "    pauser: !net.openhft.chronicle.threads.LongPauser { minPauseTimeNS: 500000, maxPauseTimeNS: 20000000, pausing: false, minBusyNS: 0, minYieldNS: 50000, busyNS: 9223372036854775807, yieldNS: 9223372036854775807, pauseTimeNS: 500000, timePaused: 0, countPaused: 0, thread: !!null \"\", yieldStart: 0, timeOutStart: 9223372036854775807, pauseUntilNS: 0, pauseStartNS: 0 },\n" +
                    "    daemon: true,\n" +
                    "    binding: none,\n" +
                    "    mediumHandlersArray: [ ],\n" +
                    "    highHandler: !net.openhft.chronicle.threads.EventHandlers NOOP,\n" +
                    "    loopStartNS: 9223372036854775807,\n" +
                    "    thread: !!null \"\",\n" +
                    "    exceptionThrownByHandler: !net.openhft.chronicle.threads.ExceptionHandlerStrategy$LogDontRemove { },\n" +
                    "    timerHandlers: [    ],\n" +
                    "    daemonHandlers: [    ],\n" +
                    "    timerIntervalMS: 1,\n" +
                    "    priorities: [\n" +
                    "      HIGH,\n" +
                    "      MEDIUM,\n" +
                    "      TIMER,\n" +
                    "      DAEMON,\n" +
                    "      MONITOR,\n" +
                    "      BLOCKING,\n" +
                    "      REPLICATION,\n" +
                    "      REPLICATION_TIMER,\n" +
                    "      CONCURRENT\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  blocking: !net.openhft.chronicle.threads.BlockingEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/blocking-event-loop,\n" +
                    "    handlers: [    ],\n" +
                    "    runners: [    ],\n" +
                    "    threadFactory: test/blocking-event-loop,\n" +
                    "    pauserSupplier: !net.openhft.chronicle.threads.PauserMode sleepy\n" +
                    "  },\n" +
                    "  pauser: !net.openhft.chronicle.threads.LongPauser {\n" +
                    "    minPauseTimeNS: 500000,\n" +
                    "    maxPauseTimeNS: 20000000,\n" +
                    "    pausing: false,\n" +
                    "    minBusyNS: 0,\n" +
                    "    minYieldNS: 50000,\n" +
                    "    busyNS: 9223372036854775807,\n" +
                    "    yieldNS: 9223372036854775807,\n" +
                    "    pauseTimeNS: 500000,\n" +
                    "    timePaused: 0,\n" +
                    "    countPaused: 0,\n" +
                    "    thread: !!null \"\",\n" +
                    "    yieldStart: 0,\n" +
                    "    timeOutStart: 9223372036854775807,\n" +
                    "    pauseUntilNS: 0,\n" +
                    "    pauseStartNS: 0\n" +
                    "  },\n" +
                    "  concPauserSupplier: !net.openhft.chronicle.threads.PauserMode sleepy,\n" +
                    "  concBinding: none,\n" +
                    "  bindingReplication: none,\n" +
                    "  priorities: [\n" +
                    "    HIGH,\n" +
                    "    MEDIUM,\n" +
                    "    TIMER,\n" +
                    "    DAEMON,\n" +
                    "    MONITOR,\n" +
                    "    BLOCKING,\n" +
                    "    REPLICATION,\n" +
                    "    REPLICATION_TIMER,\n" +
                    "    CONCURRENT\n" +
                    "  ],\n" +
                    "  concThreads: [\n" +
                    "    !!null \"\"\n" +
                    "  ],\n" +
                    "  daemon: true,\n" +
                    "  replicationPauser: !!null \"\",\n" +
                    "  replication: !!null \"\"\n" +
                    "}\n";
            if (oldExpected.equals(actual))
                return;

            String expected = "" +
                    "!net.openhft.chronicle.threads.EventGroup {\n" +
                    "  referenceId: 0,\n" +
                    "  lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "  name: test,\n" +
                    "  counter: 0,\n" +
                    "  monitor: !net.openhft.chronicle.threads.MonitorEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/~monitortest/event~loop~monitor,\n" +
                    "    handlers: [\n" +
                    "      !net.openhft.chronicle.threads.MonitorEventLoop$IdempotentLoopStartedEventHandler { referenceId: 0, handler: \"PauserMonitor<test/core-pauser>\", loopStarted: false }\n" +
                    "    ],\n" +
                    "    pauser: !net.openhft.chronicle.threads.MilliPauser { pausing: false, pauseTimeMS: 10, timePaused: 0, countPaused: 0, pauseUntilMS: 0 }\n" +
                    "  },\n" +
                    "  core: !net.openhft.chronicle.threads.VanillaEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/core-event-loop,\n" +
                    "    mediumHandlers: [    ],\n" +
                    "    newHandler: !!null \"\",\n" +
                    "    pauser: !net.openhft.chronicle.threads.LongPauser { minPauseTimeNS: 500000, maxPauseTimeNS: 20000000, pausing: false, minBusyNS: 0, minYieldNS: 50000, firstPauseNS: 9223372036854775807, pauseTimeNS: 500000, timePaused: 0, countPaused: 0, thread: !!null \"\", yieldStart: 0, pauseUntilNS: 0 },\n" +
                    "    daemon: true,\n" +
                    "    binding: none,\n" +
                    "    mediumHandlersArray: [ ],\n" +
                    "    highHandler: !net.openhft.chronicle.threads.EventHandlers NOOP,\n" +
                    "    loopStartNS: 9223372036854775807,\n" +
                    "    thread: !!null \"\",\n" +
                    "    timerHandlers: [    ],\n" +
                    "    daemonHandlers: [    ],\n" +
                    "    timerIntervalMS: 1,\n" +
                    "    priorities: [\n" +
                    "      HIGH,\n" +
                    "      MEDIUM,\n" +
                    "      TIMER,\n" +
                    "      DAEMON,\n" +
                    "      MONITOR,\n" +
                    "      BLOCKING,\n" +
                    "      REPLICATION,\n" +
                    "      REPLICATION_TIMER,\n" +
                    "      CONCURRENT\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  blocking: !net.openhft.chronicle.threads.BlockingEventLoop {\n" +
                    "    referenceId: 0,\n" +
                    "    lifecycle: !net.openhft.chronicle.threads.EventLoopLifecycle NEW,\n" +
                    "    name: test/blocking-event-loop,\n" +
                    "    handlers: [    ],\n" +
                    "    runners: [    ],\n" +
                    "    threadFactory: test/blocking-event-loop,\n" +
                    "    pauserSupplier: !net.openhft.chronicle.threads.PauserMode sleepy\n" +
                    "  },\n" +
                    "  pauser: !net.openhft.chronicle.threads.LongPauser {\n" +
                    "    minPauseTimeNS: 500000,\n" +
                    "    maxPauseTimeNS: 20000000,\n" +
                    "    pausing: false,\n" +
                    "    minBusyNS: 0,\n" +
                    "    minYieldNS: 50000,\n" +
                    "    firstPauseNS: 9223372036854775807,\n" +
                    "    pauseTimeNS: 500000,\n" +
                    "    timePaused: 0,\n" +
                    "    countPaused: 0,\n" +
                    "    thread: !!null \"\",\n" +
                    "    yieldStart: 0,\n" +
                    "    pauseUntilNS: 0\n" +
                    "  },\n" +
                    "  concPauserSupplier: !net.openhft.chronicle.threads.PauserMode sleepy,\n" +
                    "  concBinding: none,\n" +
                    "  bindingReplication: none,\n" +
                    "  priorities: [\n" +
                    "    HIGH,\n" +
                    "    MEDIUM,\n" +
                    "    TIMER,\n" +
                    "    DAEMON,\n" +
                    "    MONITOR,\n" +
                    "    BLOCKING,\n" +
                    "    REPLICATION,\n" +
                    "    REPLICATION_TIMER,\n" +
                    "    CONCURRENT\n" +
                    "  ],\n" +
                    "  concThreads: [\n" +
                    "    !!null \"\"\n" +
                    "  ],\n" +
                    "  daemon: true,\n" +
                    "  replicationPauser: !!null \"\",\n" +
                    "  replication: !!null \"\"\n" +
                    "}\n";

            assertEquals(expected, actual);
        }
    }
}
