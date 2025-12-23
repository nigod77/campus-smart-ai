package com.nijiahao.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CampusAiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusAiGatewayApplication.class, args);
    }

}
