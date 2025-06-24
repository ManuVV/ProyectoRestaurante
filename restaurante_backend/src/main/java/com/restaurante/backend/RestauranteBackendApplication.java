package com.restaurante.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RestauranteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteBackendApplication.class, args);
	}

}
