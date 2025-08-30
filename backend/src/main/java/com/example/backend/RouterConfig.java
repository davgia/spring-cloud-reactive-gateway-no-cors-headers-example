package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    private static final Logger log = LoggerFactory.getLogger(RouterConfig.class);

    public static class Response {
        private final String originalMethod;
        private final String originalUri;

        public Response(String originalMethod, String originalUri) {
            this.originalMethod = originalMethod;
            this.originalUri = originalUri;
        }

        public String getOriginalMethod() {
            return originalMethod;
        }

        public String getOriginalUri() {
            return originalUri;
        }
    }

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions.route()
                .GET("/hello", request -> {
                    log.info("GET request detected");
                    final var response = new Response("GET", request.uri().toString());
                    return ServerResponse.ok().bodyValue(response);
                })
                .POST("/hello", request -> {
                    log.info("POST request detected");
                    final var response = new Response("POST", request.uri().toString());
                    return ServerResponse.ok().bodyValue(response);
                })
                .build();
    }
}
