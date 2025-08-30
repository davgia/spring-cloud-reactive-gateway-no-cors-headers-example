package com.example.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.config.GlobalCorsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfiguration {

    private static final Logger log = LoggerFactory.getLogger(CorsConfiguration.class);

    @Bean
    public CorsWebFilter corsFilter(final GlobalCorsProperties globalCorsProperties) throws JsonProcessingException {
        final var source = new UrlBasedCorsConfigurationSource();
        globalCorsProperties.getCorsConfigurations().forEach(source::registerCorsConfiguration);

        final var mapper = new ObjectMapper();
        log.info("Cors configurations: {}", mapper.writeValueAsString(globalCorsProperties.getCorsConfigurations()));

        return new CorsWebFilter(source);
    }
}