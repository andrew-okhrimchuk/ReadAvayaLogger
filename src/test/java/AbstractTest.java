import lombok.extern.slf4j.Slf4j;
import server.ListenerAvayaClientTest;
import com.typesafe.config.Config;
import readAvayaLogger.config.Configs;
import org.junit.BeforeClass;
import org.junit.Test;
import server.JabberServerDataTest;

@Slf4j
public abstract class AbstractTest {
    private static Config date = Configs.getConfig("common.config","test_date");
   // private static Config path_to_save_files_test = Configs.getConfig("common.config","path_to_save_files_test");
  //  private static int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");

    @BeforeClass
    public static void init() throws Exception {
        log.info("Start init");
        //JabberServerDataTest jt = new JabberServerDataTest(date, length);
       // jt.start();
        //ListenerAvayaClientTest lac = new ListenerAvayaClientTest(date, path_to_save_files_test, length);
      //  lac.run();
        log.info("End init");
    }

    @Test(expected = InstantiationException.class)
    public void getWithLimit() {
        log.info("Start getWithLimit");

    }
}
