package com.citi.cmb.gce.accountservices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Import(value = { AccountServicesApplication.class })
@EnableAsync
public class ThreadConfig {

	@Bean("threadPoolExecutor")
	public TaskExecutor threadPoolTaskExecutor(Environment environment) {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5); //Set values from environment
		executor.setMaxPoolSize(10); //Set values from environment
		executor.setThreadNamePrefix("as_thread");
		executor.initialize();
		
		return executor;
	}
}
