package upbit.trade_server.setting;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan("upbit.trade_server")
@EnableAsync
public class AsyncConfig {

	@Bean(name = "fooExecutor")
	public Executor fooExecutor() {
		System.out.println("fooExecutor");
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//		taskExecutor.setCorePoolSize(5);
//		taskExecutor.setMaxPoolSize(10);
//		taskExecutor.setQueueCapacity(30);
		taskExecutor.setThreadNamePrefix("fooExecutor-");
		taskExecutor.initialize();
		return taskExecutor;
	}

	@Bean(name = "fooExecutor2")
	public Executor fooExecutor2() {
		System.out.println("fooExecutor2");
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//		taskExecutor.setCorePoolSize(1);
//		taskExecutor.setMaxPoolSize(1000000000);
//		taskExecutor.setQueueCapacity(1);
		taskExecutor.setThreadNamePrefix("fooExecutor2-");
		taskExecutor.initialize();
		return taskExecutor;
	}
}