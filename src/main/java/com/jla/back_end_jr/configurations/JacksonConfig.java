package com.jla.back_end_jr.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração global do Jackson:
 * - SNAKE_CASE (nome JSON) <-> camelCase (Java)
 * - Datas Java 8+ via JavaTimeModule
 * - ISO-8601 em vez de timestamps numéricos
 */
@Configuration
public class JacksonConfig {
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }
}
