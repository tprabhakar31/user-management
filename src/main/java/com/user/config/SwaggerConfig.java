package com.user.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	public OpenAPI openApi() {
		return new OpenAPI()
				.info(new Info()
						.title("User-Management")
						.version("1.0")
						.description("Api documentation for mymart user-management"));
	}
}
