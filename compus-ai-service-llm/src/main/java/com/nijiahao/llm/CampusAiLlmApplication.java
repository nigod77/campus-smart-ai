package com.nijiahao.llm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.nijiahao.llm.mapper")
@SpringBootApplication
public class CampusAiLlmApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusAiLlmApplication.class, args);
    }
}
