package net.openhft.chronicle.wire.channel.echo;

import net.openhft.chronicle.wire.channel.ChronicleChannel;
import net.openhft.chronicle.wire.channel.ChronicleChannelCfg;
import net.openhft.chronicle.wire.channel.ChronicleContext;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChannelSecondaryConnectionTest extends net.openhft.chronicle.wire.WireTestCommon {

    public static final String EXPECTED = "secondary is much better !";

    public interface SayMsg {
        void say(String say);
    }

    @Override
    @Before
    public void threadDump() {
        super.threadDump();
    }

    /**
     * This test verifies the behavior of the system when the primary server is unavailable. It sets up a server and a client
     * that attempt to connect to two different servers. The first server (primary) is intentionally configured to be unavailable.
     * After the client fails to connect to the primary server, it attempts to connect to the second server (secondary).
     * The secondary server is the host port configured for the gateway to run on.
     */
    @Test
    public void testEchoHandlerOnSecondaryConnection() {
        expectException("failed to connect to host-port");
        try (ChronicleContext context = ChronicleContext.newContext("tcp://:0?sessionName=testId")) {
            context.startNewGateway();

            final ChronicleChannelCfg channelCfg = new ChronicleChannelCfg().initiator(true).buffered(true);

            // this will be invalid
            channelCfg.addHostnamePort("localhost", 8092);

            // this should be valid
            channelCfg.addHostnamePort("localhost", context.port());

            try (ChronicleChannel channel = ChronicleChannel.newChannel(context.socketRegistry(), channelCfg,
                    new EchoHandler())) {
                channel.methodWriter(SayMsg.class).say(EXPECTED);

                final StringBuilder eventType = new StringBuilder();
                String actual = channel.readOne(eventType, String.class);
                assertEquals(EXPECTED, actual);
            }
        }
    }

}
