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

package net.openhft.chronicle.wire.examples;

import net.openhft.chronicle.wire.JSONWire;
import net.openhft.chronicle.wire.Wire;

public class WireExamples4 {

    interface Printer {
        void print(String message);
    }

    public static void main(String[] args) {
        Wire wire = new JSONWire();
        final Printer printer = wire.methodWriter(Printer.class);
        printer.print("hello world");
        System.out.println(wire.bytes());
        wire.methodReader((Printer) System.out::println).readOne();
    }
}
