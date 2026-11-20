package com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.adapters;

import com.aquamonitor.platform.u20241e281.sampling.domain.exceptions.DuplicatePlanCodeException;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.aggregates.SamplingPlan;
import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.PlanCode;
import com.aquamonitor.platform.u20241e281.sampling.domain.repositories.SamplingPlanRepository;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.entities.SamplingPlanJpaEntity;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.mappers.SamplingPlanPersistenceMapper;
import com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.repositories.SamplingPlanJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Locale;

/**
 * Adapter that implements the {@link SamplingPlanRepository} port with Spring Data JPA.
 * It translates a violation of the plan code UNIQUE constraint into a {@link DuplicatePlanCodeException}.
 */
@Repository
public class SamplingPlanRepositoryAdapter implements SamplingPlanRepository {

    private final SamplingPlanJpaRepository jpaRepository;
    private final SamplingPlanPersistenceMapper mapper;

    /**
     * Constructor.
     *
     * @param jpaRepository the Spring Data JPA repository
     * @param mapper        the persistence mapper
     */
    public SamplingPlanRepositoryAdapter(SamplingPlanJpaRepository jpaRepository, SamplingPlanPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    // inherited javadoc
    @Override
    public SamplingPlan save(SamplingPlan samplingPlan) {
        try {
            return mapper.toDomain(jpaRepository.saveAndFlush(mapper.toEntity(samplingPlan)));
        } catch (DataIntegrityViolationException e) {
            if (isPlanCodeUniqueViolation(e)) {
                throw new DuplicatePlanCodeException(samplingPlan.getPlanCode().code(), e);
            }
            throw e;
        }
    }

    // inherited javadoc
    @Override
    public boolean existsByPlanCode(PlanCode planCode) {
        return jpaRepository.existsByPlanCode(planCode.code());
    }

    private static boolean isPlanCodeUniqueViolation(Throwable exception) {
        for (var cause = exception; cause != null; cause = cause.getCause()) {
            var message = cause.getMessage();
            if (message != null && message.toLowerCase(Locale.ROOT)
                    .contains(SamplingPlanJpaEntity.PLAN_CODE_UNIQUE_CONSTRAINT)) {
                return true;
            }
        }
        return false;
    }
}
