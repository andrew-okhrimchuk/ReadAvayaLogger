package server;

import org.junit.Test;
import nonBlockingEchoServer.config.Configs;




public class ModulesTests extends AbstractModulesTest {
    int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");


    @Test
    public void simple () throws Exception {
        nonBlockingEchoServer.start();

        //   Thread.sleep(2000);
        ListenerAvayaClientTest jt = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt1 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt2 = new ListenerAvayaClientTest(date, length);
        jt.start();
                jt1.start();
        jt2.start();
        Thread.sleep(2000);
    }
}
