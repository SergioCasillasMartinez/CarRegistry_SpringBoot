package com.scasmar.carregistry;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarregistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarregistryApplication.class, args);
	}

	@Bean
	public OpenAPI openAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("Car Registry API")
						.version("0.1")
						.description("Example of an API connected to a data base with two classes related to each other and a user registry")
						.termsOfService("http://swagger.io/terms/")
						.license(new License()
										.name("Apache 2.0")
										.url("http://springdoc.org")));
	}

}
