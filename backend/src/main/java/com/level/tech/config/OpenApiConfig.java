package com.level.tech.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .info(new Info().title("LEVEL TECH API").version("1.0"))
                        .addSecurityItem(new SecurityRequirement().addList("JWT Authentication"))
                        .components(
                                new io.swagger.v3.oas.models.Components()
                                        .addSecuritySchemes("JWT Authentication",
                                                new SecurityScheme()
                                                        .type(SecurityScheme.Type.APIKEY)
                                                        .in(SecurityScheme.In.HEADER)
                                                        .name("Authorization") // The name of the header used for the token
                                        )
                        );
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch("/**")
                        .build();
        }
}
