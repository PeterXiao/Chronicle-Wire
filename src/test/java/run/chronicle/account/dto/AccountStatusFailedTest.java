package run.chronicle.account.dto;

import net.openhft.chronicle.wire.Marshallable;
import net.openhft.chronicle.wire.converter.ShortText;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static run.chronicle.account.dto.AccountStatusTest.getAccountStatus;

public class AccountStatusFailedTest {
    @Test
    public void testFromString() {
        AccountStatusFailed asf = Marshallable.fromString(""+
                "!run.chronicle.account.dto.AccountStatusFailed {\n" +
                "  sender: sender,\n" +
                "  target: target,\n" +
                "  sendingTime: 2001/02/03T04:05:06.007008009,\n" +
                "  accountStatus: {\n" +
                "    sender: sender,\n" +
                "    target: target,\n" +
                "    sendingTime: 2001-02-03T04:05:06.007008009,\n" +
                "    name: name,\n" +
                "    account: 2,\n" +
                "    currency: CURR,\n" +
                "    amount: 1.0" +
                "  },\n" +
                "  reason: reasons\n" +
                "}");
        assertEquals("sender", ShortText.INSTANCE.asString(asf.sender()));
        assertEquals("target", ShortText.INSTANCE.asString(asf.target()));
        assertEquals("reasons", asf.reason());
        assertEquals(getAccountStatus(), asf.accountStatus());
    }

}
