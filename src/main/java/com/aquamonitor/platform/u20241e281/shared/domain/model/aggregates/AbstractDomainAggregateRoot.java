package com.aquamonitor.platform.u20241e281.shared.domain.model.aggregates;

import com.aquamonitor.platform.u20241e281.shared.domain.model.events.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for all domain aggregate roots.
 *
 * <p>Keeps the domain events registered by the aggregate until they are pulled for publication.
 * It has no framework dependency.</p>
 */
public abstract class AbstractDomainAggregateRoot {

    private final List<DomainEvent> pendingEvents = new ArrayList<>();

    /**
     * Registers a domain event.
     *
     * @param event the domain event
     */
    protected final void registerEvent(DomainEvent event) {
        pendingEvents.add(Objects.requireNonNull(event));
    }

    /**
     * Returns and clears the registered domain events.
     *
     * @return the pending domain events
     */
    public final List<DomainEvent> pullDomainEvents() {
        var events = List.copyOf(pendingEvents);
        pendingEvents.clear();
        return events;
    }
}
