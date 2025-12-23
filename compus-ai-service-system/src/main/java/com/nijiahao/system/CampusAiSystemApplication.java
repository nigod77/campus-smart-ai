package com.nijiahao.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CampusAiSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusAiSystemApplication.class, args);
    }
}
