package server;

import com.typesafe.config.Config;
import org.junit.AfterClass;
import org.junit.Test;
import readAvayaLogger.config.Configs;
import readAvayaLogger.server.ReverseServer;


public class ModulesTests {

    @AfterClass
    public static void stop(){
        ReverseServer.stoping();
    }

    @Test
    public void simple () throws Exception {
        Config date = Configs.getConfig("common.config","test_date");
        ReverseServer.date = date;
        int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");
        ReverseServer rs = new ReverseServer();
        rs.start();

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
