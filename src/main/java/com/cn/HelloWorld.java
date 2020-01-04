package com.cn;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRabbit
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("hello world");
        SpringApplication.run(HelloWorld.class,args);
    }
}
