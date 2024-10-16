package com.producto1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Producto1Application {

	public static void main(String[] args) {
		SpringApplication.run(Producto1Application.class, args);
	}

}
