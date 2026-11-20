package com.aquamonitor.platform.u20241e281.shared.infrastructure.time.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Provides the {@link Clock} used to obtain audit instants.
 */
@Configuration
public class TimeConfiguration {

    /**
     * Creates the UTC system clock.
     *
     * @return the clock
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
