package readAvayaLogger.timer;

import com.typesafe.config.Config;
import readAvayaLogger.server.ListenerAvayaServer;
import readAvayaLogger.config.Configs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import readAvayaLogger.server.ReverseServer;

import java.util.TimerTask;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
//@ Класс слушает ReverseServer каждые 5 сек жив ли он, если нет, то запустит снова.
public class Start_of_the_fallen_thread extends TimerTask {
    private ReverseServer lac;

    public void run()   {
        log.info("Start run");
        if(lac == null){
            newListenerAvayaServer();
        }

        if (!lac.isAlive()){
            newListenerAvayaServer();
            lac.start();
        }
        else log.info("Not run new thread of ListenerAvayaClient: ReverseServer.isAlive");
        log.info("End run");
    }

    private void newListenerAvayaServer(){
        lac = new ReverseServer();
    }
}
