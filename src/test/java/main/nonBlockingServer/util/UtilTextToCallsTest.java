package main.nonBlockingServer.util;

import main.entity.CallsNew;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTextToCallsTest {


    @Test
    public void oldToNew() throws Exception {
        UtilTextToCalls utilTextToCalls = new UtilTextToCalls();
        CallsNew expected = utilTextToCalls.oldToNew(CallsData.calls);
        CallsNew actual = CallsData.callsNew;
        actual.setId(null);
        assertEquals(expected, actual);
    }
}
