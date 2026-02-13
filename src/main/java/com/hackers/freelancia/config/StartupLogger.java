package com.hackers.freelancia.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;

    public StartupLogger(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        String appName = environment.getProperty("spring.application.name");
        String port = environment.getProperty("server.port", "8080");

        System.out.println("=================================");
        System.out.println("🚀 Application : " + appName);
        System.out.println("🌐 Server URL  : http://localhost:" + port);
        System.out.println("=================================");
    }
}
