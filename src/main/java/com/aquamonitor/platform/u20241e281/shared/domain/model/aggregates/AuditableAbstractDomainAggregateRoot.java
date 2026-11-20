package com.aquamonitor.platform.u20241e281.shared.domain.model.aggregates;

import java.time.Instant;
import java.util.Objects;

/**
 * Base class for auditable aggregate roots.
 *
 * <p>Keeps the creation and last update instants. The aggregate receives them from the
 * application layer (through a {@link java.time.Clock}), so auditing is part of the domain model
 * and does not depend on JPA listeners.</p>
 */
public abstract class AuditableAbstractDomainAggregateRoot extends AbstractDomainAggregateRoot {

    private final Instant createdAt;
    private final Instant updatedAt;

    /**
     * Constructor.
     *
     * @param createdAt the creation instant
     * @param updatedAt the last update instant
     * @throws NullPointerException when an instant is null
     */
    protected AuditableAbstractDomainAggregateRoot(Instant createdAt, Instant updatedAt) {
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt must not be null");
    }

    /**
     * Gets the creation instant.
     *
     * @return the creation instant
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the last update instant.
     *
     * @return the last update instant
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
