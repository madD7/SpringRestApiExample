package com.example.practicaljava.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
/**
 * This class is used for Documentation.
 * Open the url - http://localhost:8001/swagger-ui.html 
 * for auto documentation.
 */
public class OpenApiConfig {

	@Bean
	public OpenAPI practicalJavaOpenApi() {
		var info = new Info().title("Practical Java API")
							.description("OpenAPI (Swagger) Documentation auto generated from code.")
							.version("1.0");
		return new OpenAPI().info(info);
	}
}
