package server;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import nonBlockingEchoServer.config.Configs;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static nonBlockingEchoServer.server.NonBlockingEchoServer.countTextTest;
import static org.hamcrest.CoreMatchers.is;


public class ModulesTests extends AbstractModulesTest {
    int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");




    @Test
    public void simple () throws Exception {
        if(!nonBlockingEchoServer.isAlive()){nonBlockingEchoServer.start();}

        //   Thread.sleep(2000);
        ListenerAvayaClientTest jt = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt1 = new ListenerAvayaClientTest(date, length);
        ListenerAvayaClientTest jt2 = new ListenerAvayaClientTest(date, length);
        jt.start();
                jt1.start();
        jt2.start();
        //Thread.sleep(2000);
    }
    @Test
    public void notSimple () throws Exception {
        if(!nonBlockingEchoServer.isAlive()){nonBlockingEchoServer.start();}
        countTextTest.clear();

        ListenerAvayaClientTest_2 jt = new ListenerAvayaClientTest_2(date, length);
        jt.start();
        int sum = 0;
        Thread.sleep(2500);

        Iterator<Integer> iterator = countTextTest.iterator();
        while (iterator.hasNext())
        {
            sum +=iterator.next();
        }
        System.out.println("sum = " + sum);
        Assert.assertThat(sum , is(76680)); // = 15 * 5112
    }
}
