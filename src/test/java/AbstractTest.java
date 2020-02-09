import readAvayaLogger.client.ListenerAvayaClient;
import com.typesafe.config.Config;
import readAvayaLogger.config.Configs;
import org.junit.BeforeClass;
import org.junit.Test;
import server.JabberServerDataTest;

public abstract class AbstractTest {
    private static Config date = Configs.getConfig("common.config","test_date");
    private static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private static int length = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");

    @BeforeClass
    public static void init() throws Exception {
        JabberServerDataTest jt = new JabberServerDataTest(date, length);
        jt.start();
        ListenerAvayaClient lac = new ListenerAvayaClient(date, path_to_save_files, length);
        lac.run();
    }

    @Test(expected = InstantiationException.class)
    public void getWithLimit() {

    }
}
