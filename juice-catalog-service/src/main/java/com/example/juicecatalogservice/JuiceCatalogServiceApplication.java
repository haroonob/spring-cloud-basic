package com.example.juicecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class JuiceCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuiceCatalogServiceApplication.class, args);
	}
}
