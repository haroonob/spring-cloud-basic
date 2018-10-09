package com.example.juiceedgeservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class JuiceEdgeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JuiceEdgeServiceApplication.class, args);
	}
}

@Data
//@AllArgsConstructor
class Juice {
	private String name;
}
@FeignClient("juice-catalog-service")
interface JuiceClient {

	@GetMapping("/juices")
	Resources<Juice> readJuices();
}

@RestController
class GoodJuiceApiAdapterRestController {

	private final JuiceClient juiceClient;

	public GoodJuiceApiAdapterRestController(JuiceClient juiceClient) {
		this.juiceClient = juiceClient;
	}

	@GetMapping("/good-juices")
	public Collection<Juice> goodJuices() {
		return juiceClient.readJuices()
				.getContent()
				.stream()
				.filter(this::isGreat)
				.collect(Collectors.toList());
	}

	private boolean isGreat(Juice juice) {
		return !juice.getName().equals("Budweiser") &&
				!juice.getName().equals("Coors Light") &&
				!juice.getName().equals("PBR");
	}
}