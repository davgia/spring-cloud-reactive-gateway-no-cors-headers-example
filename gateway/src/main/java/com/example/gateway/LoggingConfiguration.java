package com.example.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

@Configuration
public class LoggingConfiguration {
    private static final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Bean
    public WebFilter getLoggingFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            final var logPrefix = exchange.getLogPrefix();
            final var request = exchange.getRequest();

            final var requestMethod = request.getMethod().toString();
            final var requestUri = request.getURI().toString();

            final var routeId = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
            final var routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);

            log.debug("{}Incoming {} request {} is routed to {} (id: {}, headers: {})", logPrefix, requestMethod, requestUri, routeUri, routeId,                     exchange.getRequest().getHeaders().entrySet().stream()
                    .filter(stringListEntry -> !stringListEntry.getKey().equals("Authorization")).toList());

            return chain.filter(exchange).doFinally((signalType) -> {
                log.info("{}Fulfilling request with status {} (signal type: {})", logPrefix, exchange.getResponse().getStatusCode(), signalType);
            });
        };
    }
}
