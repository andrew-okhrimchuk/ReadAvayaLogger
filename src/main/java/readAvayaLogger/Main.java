package readAvayaLogger;

import lombok.extern.slf4j.Slf4j;
import readAvayaLogger.timer.MyShutdownHook;
import readAvayaLogger.timer.Start_of_the_fallen_thread;
import readAvayaLogger.config.Configs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    private final static long time = (long)(Configs.getConfig("common.config","ThreadLifeCheckTimeSecounds").getInt("time")*1000);
    private final static long delay  = 1000L;

    public static void main(String[] args) {
        log.info("Start main");
        Start_of_the_fallen_thread start_of_the_fallen_thread = new Start_of_the_fallen_thread();
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
         executor.scheduleAtFixedRate(start_of_the_fallen_thread, delay, time, TimeUnit.MILLISECONDS);
        Runtime.getRuntime().addShutdownHook(new MyShutdownHook(executor));
        log.info("End main");
    }
}
