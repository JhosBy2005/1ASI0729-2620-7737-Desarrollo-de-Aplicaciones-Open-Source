package com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.repositories;

import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.entities.SamplingPlanJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for {@link SamplingPlanJpaEntity}.
 */
@Repository
public interface SamplingPlanJpaRepository extends JpaRepository<SamplingPlanJpaEntity, Long> {

    /**
     * Checks whether a plan code exists.
     *
     * @param planCode the plan code
     * @return {@code true} when it exists
     */
    boolean existsByPlanCode(String planCode);
}
