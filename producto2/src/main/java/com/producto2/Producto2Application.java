package com.producto2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Producto2Application {

	public static void main(String[] args) {
		SpringApplication.run(Producto2Application.class, args);
	}

}
