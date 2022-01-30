package com.example.n11graduationproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("credit-app")
                .pathsToMatch("/serkanbodur/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {

        Contact contact = new Contact();
        contact.email("sbodur25@gmail.com");
        contact.name("Serkan Bodur");

        return new OpenAPI().info(new Info()
                .title("Credit API")
                .version("1.0.0")
                .description("This is a credit apply project")
                .contact(contact)
                .termsOfService("http://swagger.io/terms/"));

    }

}
