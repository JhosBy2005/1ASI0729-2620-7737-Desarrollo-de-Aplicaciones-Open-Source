package com.aquamonitor.platform.u20241e281.sampling.infrastructure.persistence.jpa.entities;

import com.aquamonitor.platform.u20241e281.sampling.domain.model.valueobjects.SamplingPlanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.time.LocalDate;

/**
 * JPA entity of the {@code sampling_plans} table. Value objects are stored as flat columns
 * and the plan code is protected by a UNIQUE constraint.
 */
@Entity
@Table(name = "sampling_plans",
        uniqueConstraints = @UniqueConstraint(name = SamplingPlanJpaEntity.PLAN_CODE_UNIQUE_CONSTRAINT,
                columnNames = "plan_code"))
public class SamplingPlanJpaEntity {

    /** Name of the UNIQUE constraint of the plan code column. */
    public static final String PLAN_CODE_UNIQUE_CONSTRAINT = "uk_sampling_plans_plan_code";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_code", nullable = false, length = 50)
    private String planCode;

    @Column(name = "station_id", nullable = false, length = 36)
    private String stationId;

    @Column(name = "sampling_window_start_date", nullable = false)
    private LocalDate samplingWindowStartDate;

    @Column(name = "sampling_window_end_date", nullable = false)
    private LocalDate samplingWindowEndDate;

    @Column(name = "sample_volume_quantity", nullable = false)
    private Integer sampleVolumeQuantity;

    @Column(name = "sample_volume_unit", nullable = false, length = 50)
    private String sampleVolumeUnit;

    @Column(nullable = false)
    private Integer priorityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SamplingPlanStatus status;

    @Column(columnDefinition = "TEXT")
    private String samplingNotes;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    /** @return the id */
    public Long getId() { return id; }

    /** @param id the id */
    public void setId(Long id) { this.id = id; }

    /** @return the plan code */
    public String getPlanCode() { return planCode; }

    /** @param planCode the plan code */
    public void setPlanCode(String planCode) { this.planCode = planCode; }

    /** @return the station id */
    public String getStationId() { return stationId; }

    /** @param stationId the station id */
    public void setStationId(String stationId) { this.stationId = stationId; }

    /** @return the window start date */
    public LocalDate getSamplingWindowStartDate() { return samplingWindowStartDate; }

    /** @param samplingWindowStartDate the window start date */
    public void setSamplingWindowStartDate(LocalDate samplingWindowStartDate) {
        this.samplingWindowStartDate = samplingWindowStartDate;
    }

    /** @return the window end date */
    public LocalDate getSamplingWindowEndDate() { return samplingWindowEndDate; }

    /** @param samplingWindowEndDate the window end date */
    public void setSamplingWindowEndDate(LocalDate samplingWindowEndDate) {
        this.samplingWindowEndDate = samplingWindowEndDate;
    }

    /** @return the volume quantity */
    public Integer getSampleVolumeQuantity() { return sampleVolumeQuantity; }

    /** @param sampleVolumeQuantity the volume quantity */
    public void setSampleVolumeQuantity(Integer sampleVolumeQuantity) { this.sampleVolumeQuantity = sampleVolumeQuantity; }

    /** @return the volume unit */
    public String getSampleVolumeUnit() { return sampleVolumeUnit; }

    /** @param sampleVolumeUnit the volume unit */
    public void setSampleVolumeUnit(String sampleVolumeUnit) { this.sampleVolumeUnit = sampleVolumeUnit; }

    /** @return the priority level */
    public Integer getPriorityLevel() { return priorityLevel; }

    /** @param priorityLevel the priority level */
    public void setPriorityLevel(Integer priorityLevel) { this.priorityLevel = priorityLevel; }

    /** @return the status */
    public SamplingPlanStatus getStatus() { return status; }

    /** @param status the status */
    public void setStatus(SamplingPlanStatus status) { this.status = status; }

    /** @return the notes */
    public String getSamplingNotes() { return samplingNotes; }

    /** @param samplingNotes the notes */
    public void setSamplingNotes(String samplingNotes) { this.samplingNotes = samplingNotes; }

    /** @return the creation instant */
    public Instant getCreatedAt() { return createdAt; }

    /** @param createdAt the creation instant */
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    /** @return the last update instant */
    public Instant getUpdatedAt() { return updatedAt; }

    /** @param updatedAt the last update instant */
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
