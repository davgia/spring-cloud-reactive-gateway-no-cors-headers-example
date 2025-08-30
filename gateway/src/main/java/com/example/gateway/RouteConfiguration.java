package com.example.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RouteConfiguration {
    @Bean
    public RouteLocator backendRouterLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("backend", r -> r
                        .order(Ordered.LOWEST_PRECEDENCE)
                        .path("/hello")
                        .uri("http://host.docker.internal:8082")
                )
                .build();
    }
}
