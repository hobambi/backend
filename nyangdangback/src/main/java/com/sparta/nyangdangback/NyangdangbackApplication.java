package com.sparta.nyangdangback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NyangdangbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(NyangdangbackApplication.class, args);
    }

}
