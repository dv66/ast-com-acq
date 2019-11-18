package com.cokreates.grp.ast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.cokreates.grp")
@EnableFeignClients(basePackages = "com.cokreates")
public class ComAcq extends SpringBootServletInitializer  {

    public static void main(String[] args) {
        SpringApplication.run(ComAcq.class, args);
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ComAcq.class);
	}

}
