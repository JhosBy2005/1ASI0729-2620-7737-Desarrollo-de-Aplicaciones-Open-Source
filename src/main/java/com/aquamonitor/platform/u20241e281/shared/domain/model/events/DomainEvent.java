package com.aquamonitor.platform.u20241e281.shared.domain.model.events;

import java.time.Instant;

/**
 * Marker contract for domain events.
 */
public interface DomainEvent {

    /**
     * Gets the instant when the event occurred.
     *
     * @return the occurrence instant
     */
    Instant occurredOn();
}
