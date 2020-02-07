import client.ListenerAvayaClient;
import client.Start_of_the_fallen_thread;
import com.typesafe.config.Config;
import config.Configs;

import java.io.IOException;

public class Main {
    private static Config date = Configs.getConfig("common.config","work_date");
    private static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private static int length_lines_in_one_file = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");

    public static void main(String[] args) {
        ListenerAvayaClient lac = new ListenerAvayaClient(date, path_to_save_files, length_lines_in_one_file);
        Start_of_the_fallen_thread   start_of_the_fallen_thread = new Start_of_the_fallen_thread(lac);
        start_of_the_fallen_thread.start();


    }
}
