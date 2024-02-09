package com.app.productservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI baseOpenApiConfig() {
        ApiResponse badRequestResponse = new ApiResponse().content(new Content()
                .addMediaType("application/json", new MediaType()
                        .addExamples("default", new Example()
                                .value("{\"code\": 400, \"message\":\"Bad request\"}"))));
        ApiResponse internalServerResponse = new ApiResponse().content(new Content()
                .addMediaType("application/json", new MediaType()
                        .addExamples("default", new Example()
                                .value("{\"code\": 500, \"message\":\"Internal Server Error\"}"))));
        ApiResponse successfulResponse = new ApiResponse().content(new Content()
                .addMediaType("application/json", new MediaType()
                        .addExamples("default",new Example().value("{\"productId\": \"string\"," +
                                "\"productName\": \"string\"" +
                                "\"productPrice\": 0" +
                                "\"currency\": \"string\"" +
                                "\"currency\": \"string\"}"))));
        ApiResponse createResponse = new ApiResponse().content(new Content()
                .addMediaType("application/json", new MediaType()
                        .addExamples("default", new Example().value("{\"message\":\"default message\", \"success\":\"default Value(true)\"}"))));
        Components components = new Components();
        components.addResponses("badRequest",badRequestResponse);
        components.addResponses("internalServerResponse",internalServerResponse);
        components.addResponses("successfulResponse", successfulResponse);
        components.addResponses("createResponse", createResponse);
        return new OpenAPI().components(components)
                .info(new Info()
                        .title("Product Service Documentation")
                        .version("1.0.0")
                        .description("Product service manages all the project related endpoints and persists details to the database."));
    }
}
