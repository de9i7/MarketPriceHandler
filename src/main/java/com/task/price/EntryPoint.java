package com.task.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.lang.management.ManagementFactory;

@SpringBootApplication
public class EntryPoint {

    private static final Logger LOG = LoggerFactory.getLogger(EntryPoint.class);

    private static ApplicationContext appContext = null;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EntryPoint.class);
        appContext = app.run(args);

        LOG.info("PID: {} context {}", ManagementFactory.getRuntimeMXBean().getName(), appContext);
    }
}
