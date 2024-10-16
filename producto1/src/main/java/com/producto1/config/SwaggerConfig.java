package com.producto1.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Producto1",
                version = "1.0.0",
                description = "Endpoints del microservice producto1"
        )
)
public class SwaggerConfig {

        @Bean
        public OpenAPI springShopOpenAPI() {
                return new OpenAPI();
        }

}
