package server;

import com.typesafe.config.Config;
import nonBlockingEchoServer.config.Configs;
import nonBlockingEchoServer.server.ListenerAvayaReadNIO;
import nonBlockingEchoServer.server.NonBlockingEchoServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.net.InetSocketAddress;



public abstract class AbstractModulesTest {
    public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files_test");
    public static Config date = Configs.getConfig("common.config","test_date");
    public static NonBlockingEchoServer nonBlockingEchoServer;

    @BeforeClass
    public static void before (){
        nonBlockingEchoServer = new NonBlockingEchoServer();
        nonBlockingEchoServer.setListenAddress(new InetSocketAddress(Integer.parseInt(date.getString("port"))));
        NonBlockingEchoServer.path_to_save_files = path_to_save_files;
        ListenerAvayaReadNIO.path_to_save_files = path_to_save_files;

    }

    @AfterClass
    public static void stop(){
        NonBlockingEchoServer.stoping();
        nonBlockingEchoServer.interrupt();
    }
}
