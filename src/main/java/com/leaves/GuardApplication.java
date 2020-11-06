package com.leaves;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GuardApplication {

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(GuardApplication.class);
        app.run(args);
    }


}
