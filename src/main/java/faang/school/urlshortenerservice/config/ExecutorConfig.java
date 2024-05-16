package faang.school.urlshortenerservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    @Value("${executorConfig.core}")
    private int corePoolSize;
    @Value("${executorConfig.max}")
    private int maxPoolSize;

    @Bean
    public ExecutorService executor() { //TODO: надо ещё подумать
        return Executors.newFixedThreadPool(corePoolSize);
    }

}