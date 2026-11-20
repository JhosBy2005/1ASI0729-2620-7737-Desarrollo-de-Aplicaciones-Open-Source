package com.aquamonitor.platform.u20241e281.shared.infrastructure.serialization.jackson.configuration;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers additional Jackson modules.
 *
 * <p>The {@link JsonNullableModule} allows request resources to distinguish between an omitted
 * JSON attribute and an attribute explicitly sent with {@code null}.</p>
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Creates the Jackson module for {@code JsonNullable} attributes.
     * Spring Boot registers every Jackson module bean in the application ObjectMapper.
     *
     * @return the JsonNullable Jackson module
     */
    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
}
