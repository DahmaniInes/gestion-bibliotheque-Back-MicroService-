package com.example.livraison;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;



@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.example.livraison.feign")

public class LivraisonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivraisonApplication.class, args);
    }

}
