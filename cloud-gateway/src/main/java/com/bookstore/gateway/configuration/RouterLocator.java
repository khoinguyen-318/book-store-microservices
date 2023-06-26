package com.bookstore.gateway.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouterLocator.class);
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("Category-services",r->r.path("/api/v1/category/**").uri("lb://CATEGORY-SERVICES"))
                .route("Book-services",r->r.path("/api/v1/books/**").uri("lb://BOOK-SERVICES"))
                .route("Cart-services",r->r.path("/api/v1/cart/**").uri("lb://CART-SERVICES"))
                .route("Order-services",r->r.path("/api/v1/orders/**").uri("lb://ORDER-SERVICES"))
                .route("Payment-services",r->r.path("/api/v1/payment/**").uri("lb://PAYMENT-SERVICES"))
                .route("Shipment-services",r->r.path("/api/v1/shipment/**").uri("lb://SHIPMENT-SERVICES"))
                .build();
    }
}
