package com.bookstore.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterLocator {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Category-services",r->r.path("/api/v1/category/**").uri("lb://Category-services"))
                .route("Book-services",r->r.path("/api/v1/books/**").uri("lb://Book-services"))
                .route("Cart-services",r->r.path("/api/v1/cart/**").uri("lb://Cart-services"))
                .build();
    }
}
