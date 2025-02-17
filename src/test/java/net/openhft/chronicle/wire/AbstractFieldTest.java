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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class AbstractFieldTest extends WireTestCommon {

    private final WireType wireType;

    public AbstractFieldTest(WireType wireType) {
        this.wireType = wireType;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> wireTypes() {
        return Arrays.asList(
                new Object[]{WireType.BINARY},
                new Object[]{WireType.BINARY_LIGHT},
                new Object[]{WireType.TEXT},
                new Object[]{WireType.YAML},
                new Object[]{WireType.YAML_ONLY},
                new Object[]{WireType.JSON_ONLY}
        );
    }

    @Test
    public void abstractField() {
        MSDMHolder holder = new MSDMHolder();
        holder.marshallable = new MySelfDescribingMarshallable("Hello World");

        final Wire wire = wireType.apply(Bytes.allocateElasticOnHeap());
        wire.getValueOut().object(MSDMHolder.class, holder);

        MSDMHolder result = wire.getValueIn().object(MSDMHolder.class);
        assertEquals(holder, result);
    }

    @Test
    public void abstractField2() {
        MSDMHolder2 holder = new MSDMHolder2();
        holder.marshallable = new MySelfDescribingMarshallable("Hello World");

        final Wire wire = wireType.apply(Bytes.allocateElasticOnHeap());
        wire.getValueOut().object(MSDMHolder2.class, holder);

        MSDMHolder2 result = wire.getValueIn().object(MSDMHolder2.class);
        assertEquals(holder, result);
    }

    static class MSDMHolder extends SelfDescribingMarshallable {
        SelfDescribingMarshallable marshallable;
    }

    static class MSDMHolder2 extends SelfDescribingMarshallable {
        MySelfDescribingMarshallable marshallable;
    }

    static class MySelfDescribingMarshallable extends SelfDescribingMarshallable {
        String text;

        public MySelfDescribingMarshallable(String s) {
            text = s;
        }
    }
}
