package main.nonBlockingServer.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class AsyncExecutorConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExecutorConfiguration.class);
    @Bean (name = "taskExecutor")
    public Executor taskExecutor() {
        LOGGER.debug("Creating Async Task Executor");
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("CallsThread-");
        executor.initialize();
        return executor;
    }

    @Deprecated
    @Bean (name = "newSingleThreadExecutor")
    public Executor newSingleThreadExecutor() {
        LOGGER.debug("Creating Async newSingleThreadExecutor");
        ExecutorService executor = Executors.newSingleThreadExecutor();
        return executor;
    }


}