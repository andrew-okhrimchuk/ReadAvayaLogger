package readAvayaLogger.timer;

import readAvayaLogger.client.ListenerAvayaClient;
import com.typesafe.config.Config;
import readAvayaLogger.config.Configs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class Start_of_the_fallen_thread extends TimerTask {
    private final static Config date = Configs.getConfig("common.config","work_date");
    private final static Config path_to_save_files = Configs.getConfig("common.config","path_to_save_files");
    private final static int length_lines_in_one_file = Configs.getConfig("common.config","length_lines_in_one_file").getInt("length");
    private ListenerAvayaClient lac;


    public void run()   {
        log.info("Start run");
        if(lac == null){
            newListenerAvayaClient();
        }

        if (!lac.isAlive()){
            newListenerAvayaClient();
            lac.start();
        }
        else log.info("Not run new thread of ListenerAvayaClient: ListenerAvayaClient.isAlive");
        log.info("End run");
    }

    private void newListenerAvayaClient(){
        lac = new ListenerAvayaClient(date, path_to_save_files, length_lines_in_one_file);
    }
}
