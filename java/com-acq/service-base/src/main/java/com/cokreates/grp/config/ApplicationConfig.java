package com.cokreates.grp.config;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@Configuration
@EnableTransactionManagement
public class ApplicationConfig {
	
	@PreDestroy
	public void onShutDown() {
		log.warn("Application closing...");
	}
	
}