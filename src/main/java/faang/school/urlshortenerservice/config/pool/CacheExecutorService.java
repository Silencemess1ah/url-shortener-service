package faang.school.urlshortenerservice.config.pool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class CacheExecutorService {
    @Value("${thread-pool.hash.core-pool-size}")
    private int corePoolSize;
    @Value("${thread-pool.hash.maximum-pool-size}")
    private int maxPoolSize;
    @Value("${thread-pool.hash.queue-capacity}")
    private int queueCapacity;
    @Value("${thread-pool.hash.await-termination-second}")
    private int awaitTerminationSecond;

    @Bean
    public TaskExecutor cacheThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("hash-generator-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSecond);
        executor.initialize();
        return executor;
    }
}