package main.nonBlockingEchoServer.util;

import main.entity.CallsNew;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilTextTest {


    @Test
    public void oldToNew() throws Exception {
        UtilText utilText = new UtilText();
        CallsNew expected = utilText.oldToNew(CallsData.calls);
        CallsNew actual = CallsData.callsNew;
        actual.setId(null);
        assertEquals(expected, actual);
    }
}
