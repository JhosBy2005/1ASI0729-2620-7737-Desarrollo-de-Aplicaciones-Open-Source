package com.daimlertruck.platform.u20241e281;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Bootstrap class for the Daimler Truck Platform application.
 *
 * <p>Initializes Spring Boot auto-configuration and JPA auditing infrastructure,
 * so aggregate roots keep track of their creation and last update dates.</p>
 *
 * @author José Antonio Santana Luna
 */
@EnableJpaAuditing
@SpringBootApplication
public class DaimlerTruckPlatformApplication {

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DaimlerTruckPlatformApplication.class, args);
    }
}
