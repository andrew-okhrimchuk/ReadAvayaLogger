package nonBlockingEchoServer.timer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nonBlockingEchoServer.server.NonBlockingEchoServer;

import java.util.TimerTask;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
//@ Класс слушает ReverseServer каждые 5 сек жив ли он, если нет, то запустит снова.
public class Start_of_the_fallen_thread_NIO extends TimerTask {
    private NonBlockingEchoServer lac;

    public void run()   {
        //log.info("Start run");
        if(lac == null){
            nonBlockingEchoServer();
        }

        if (!lac.isAlive()){
            nonBlockingEchoServer();
            lac.start();
        }
       // else log.info("Not run new thread of ReverseServer: ReverseServer.isAlive");
     //   log.info("End run");
    }

    private void nonBlockingEchoServer(){
        log.info("Start new NonBlockingEchoServer");
        lac = new NonBlockingEchoServer();
    }
}
