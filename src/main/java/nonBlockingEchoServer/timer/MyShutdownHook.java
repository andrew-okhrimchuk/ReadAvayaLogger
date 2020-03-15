package nonBlockingEchoServer.timer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;

@Data
@AllArgsConstructor
@Slf4j
@RequiredArgsConstructor
public class MyShutdownHook extends Thread {
    private ScheduledExecutorService executor;

    @Override
    public void run() {
        log.info("=== my shutdown hook activated");
        executor.shutdown();
      //  StopTest.running = false;

    }

}
