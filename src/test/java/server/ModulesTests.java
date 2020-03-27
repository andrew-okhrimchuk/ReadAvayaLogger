package server;

import org.junit.Test;
import main.nonBlockingEchoServer.config.Configs;

//import static nonBlockingEchoServer.server.NonBlockingEchoServer.countTextTest;


public class ModulesTests extends AbstractModulesTest {
    int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");


/*

    @Test
    public void simple () throws Exception {
        if(!nonBlockingEchoServer.isAlive()){nonBlockingEchoServer.start();}

        Thread.sleep(2000);
        ListenerAvayaClientTest jt = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt1 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt2 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt3 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt4 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt5 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt6 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt7 = new ListenerAvayaClientTest(date, length);
        jt.start();
        jt1.start();
        jt2.start();
        jt3.start();
        jt4.start();
        jt5.start();
        jt6.start();
        jt7.start();

    }
    @Test
    public void notSimple () throws Exception {
        Thread.sleep(2000);
        if(!nonBlockingEchoServer.isAlive()){nonBlockingEchoServer.start();}
//        countTextTest = new AtomicInteger();

        ListenerAvayaClientTest_2 jt1 = new ListenerAvayaClientTest_2(date, length);
        ListenerAvayaClientTest_2 jt2 = new ListenerAvayaClientTest_2(date, length);
        ListenerAvayaClientTest_2 jt3 = new ListenerAvayaClientTest_2(date, length);
        jt1.start();
        Thread.sleep(150);
        jt2.start();
        Thread.sleep(150);
        jt3.start();
        Thread.sleep(2*1000);*/


//        System.out.println("countTextTest = " + countTextTest);
//       System.out.println("countTextTest / 5112 = " + (countTextTest.get()/5112));
 //       Assert.assertThat(countTextTest.get() , is(92016)); // = 15 * 5112
 //   }
}
