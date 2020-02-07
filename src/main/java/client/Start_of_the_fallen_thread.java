package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
public class Start_of_the_fallen_thread extends Thread {
    private ListenerAvayaClient lac;
    private static Thread thread;

    public void run()   {
        while (thread == null || !thread.isAlive()){
             lac.run();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("Not run new thread of ListenerAvayaClient, Exception = " + e.getMessage());
            }
        }
    }
}
