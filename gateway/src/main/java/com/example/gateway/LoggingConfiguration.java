package com.example.gateway;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

@Configuration
public class LoggingConfiguration {
    private final Log log = LogFactory.getLog(getClass());

    @Bean
    public GlobalFilter getLoggingFilter() {
        return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
            final var uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
            final var originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
            final var route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            final var routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);

            log.info("Incoming request " + originalUri + " is routed to id: " + route + ", uri:" + routeUri);

            return chain.filter(exchange);
        };
    }
}
