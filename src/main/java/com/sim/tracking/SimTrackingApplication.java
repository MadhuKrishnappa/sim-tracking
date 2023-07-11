package com.sim.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.sim.tracking")
@EnableTransactionManagement
//@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)

@SuppressWarnings("unused")
public class SimTrackingApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SimTrackingApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimTrackingApplication.class);
	}

}
