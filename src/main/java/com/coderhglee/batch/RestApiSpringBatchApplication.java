package com.coderhglee.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class RestApiSpringBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiSpringBatchApplication.class, args);
    }

}
