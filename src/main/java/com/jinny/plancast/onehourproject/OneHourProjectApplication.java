package com.jinny.plancast.onehourproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class OneHourProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneHourProjectApplication.class, args);
    }

}
