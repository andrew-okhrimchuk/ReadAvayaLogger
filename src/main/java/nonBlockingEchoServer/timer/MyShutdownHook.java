package nonBlockingEchoServer.timer;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

@AllArgsConstructor
@Slf4j
@RequiredArgsConstructor
public class MyShutdownHook extends Thread {
    private ExecutorService executor;

    @Override
    public void run() {
        log.info("=== my shutdown hook activated");
        executor.shutdown();
    }

}
