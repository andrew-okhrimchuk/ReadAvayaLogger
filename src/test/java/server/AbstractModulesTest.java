package server;

import com.typesafe.config.Config;
import main.nonBlockingEchoServer.config.Configs;
import main.nonBlockingEchoServer.server.ListenerAvayaReadNIO_NEW;
import main.nonBlockingEchoServer.server.NonBlockingEchoServer_NEW;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.net.InetSocketAddress;



public abstract class AbstractModulesTest {
  /*  public static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files_test");
    public static Config date = Configs.getConfig("common.config","test_date");
    public static NonBlockingEchoServer_NEW nonBlockingEchoServer;

    @BeforeClass
    public static void before (){
        nonBlockingEchoServer = new NonBlockingEchoServer_NEW();
        nonBlockingEchoServer.setListenAddress(new InetSocketAddress(Integer.parseInt(date.getString("port"))));
        NonBlockingEchoServer_NEW.path_to_save_files = path_to_save_files;
        ListenerAvayaReadNIO_NEW.path_to_save_files = path_to_save_files;

    }

    @AfterClass
    public static void stop(){
        NonBlockingEchoServer_NEW.stoping();
        nonBlockingEchoServer.interrupt();
    }*/
}
