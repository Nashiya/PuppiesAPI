package com.redthreadcomm.puppies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PuppiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PuppiesApplication.class, args);
    }

}
