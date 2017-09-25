package info.smartkit.godpaper.go.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by smartkit on 25/09/2017.
 */
@Configuration
public class AppConfig {
        @Bean
        public ThreadPoolTaskExecutor taskExecutor() {
                ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
                pool.setCorePoolSize(5);
                pool.setMaxPoolSize(10);
                pool.setWaitForTasksToCompleteOnShutdown(true);
                return pool;
        }
}
