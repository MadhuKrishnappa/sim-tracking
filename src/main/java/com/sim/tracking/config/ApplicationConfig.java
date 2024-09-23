package com.sim.tracking.config;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ApplicationConfig {

    @Bean(name = "authenticationFilter")
    public Filter authenticationFilter() {
        return new AuthenticationFilter();
    }
}
