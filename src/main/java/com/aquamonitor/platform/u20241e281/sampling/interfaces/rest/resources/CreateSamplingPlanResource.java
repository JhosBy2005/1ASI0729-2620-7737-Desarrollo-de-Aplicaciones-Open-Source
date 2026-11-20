package com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.resources;

import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation.IsoLocalDate;
import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation.ValidSamplingPlanStatus;
import com.aquamonitor.platform.u20241e281.sampling.interfaces.rest.validation.ValidSamplingWindow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Request resource to register a new sampling plan.
 *
 * <p>It is a mutable class (not a record) so Jackson calls {@link #setStatus(String)} only when the
 * {@code status} attribute is present in the JSON body. That is how an omitted status (PENDING is assigned)
 * is distinguished from a status sent with {@code null} (rejected with HTTP 400).</p>
 */
@ValidSamplingWindow
@ValidSamplingPlanStatus
@Schema(name = "CreateSamplingPlanRequest", description = "Request payload to register a sampling plan")
public class CreateSamplingPlanResource {

    /** Regular expression of a UUID. */
    public static final String UUID_REGEX =
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    @NotBlank(message = "{validation.not-blank}")
    @Size(max = 50, message = "{validation.size.max}")
    @Schema(description = "Plan code, unique", example = "SAM-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String planCode;

    @NotBlank(message = "{validation.not-blank}")
    @Pattern(regexp = UUID_REGEX, message = "{validation.uuid}")
    @Schema(description = "Monitoring station id (UUID)", example = "550e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String stationId;

    @NotBlank(message = "{validation.not-blank}")
    @IsoLocalDate(message = "{validation.date.iso}")
    @Schema(description = "Sampling window start date (yyyy-MM-dd)", example = "2026-11-21", format = "date",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String samplingWindowStartDate;

    @NotBlank(message = "{validation.not-blank}")
    @IsoLocalDate(message = "{validation.date.iso}")
    @Schema(description = "Sampling window end date (yyyy-MM-dd), after the start date", example = "2026-11-25",
            format = "date", requiredMode = Schema.RequiredMode.REQUIRED)
    private String samplingWindowEndDate;

    @NotNull(message = "{validation.not-null}")
    @Positive(message = "{validation.positive}")
    @Schema(description = "Sample volume quantity, greater than 0", example = "10",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sampleVolumeQuantity;

    @NotBlank(message = "{validation.not-blank}")
    @Size(max = 50, message = "{validation.size.max}")
    @Schema(description = "Sample volume unit", example = "LITER", requiredMode = Schema.RequiredMode.REQUIRED)
    private String sampleVolumeUnit;

    @NotNull(message = "{validation.not-null}")
    @Min(value = 1, message = "{validation.priority-level.range}")
    @Max(value = 5, message = "{validation.priority-level.range}")
    @Schema(description = "Priority level from 1 (lowest) to 5 (highest)", example = "3",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer priorityLevel;

    @Schema(description = "Optional status, PENDING when omitted", example = "PENDING",
            allowableValues = {"PENDING", "APPROVED", "COMPLETED", "CANCELLED"},
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String status;

    @JsonIgnore
    private boolean statusPresent;

    @Schema(description = "Optional notes", example = "Water quality sampling for river monitoring",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String samplingNotes;

    /** @return the plan code */
    public String getPlanCode() { return planCode; }

    /** @param planCode the plan code */
    public void setPlanCode(String planCode) { this.planCode = planCode; }

    /** @return the station id */
    public String getStationId() { return stationId; }

    /** @param stationId the station id */
    public void setStationId(String stationId) { this.stationId = stationId; }

    /** @return the window start date */
    public String getSamplingWindowStartDate() { return samplingWindowStartDate; }

    /** @param samplingWindowStartDate the window start date */
    public void setSamplingWindowStartDate(String samplingWindowStartDate) {
        this.samplingWindowStartDate = samplingWindowStartDate;
    }

    /** @return the window end date */
    public String getSamplingWindowEndDate() { return samplingWindowEndDate; }

    /** @param samplingWindowEndDate the window end date */
    public void setSamplingWindowEndDate(String samplingWindowEndDate) {
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

    /** @return the status, or {@code null} */
    public String getStatus() { return status; }

    /**
     * Sets the status. Jackson only calls this setter when the attribute is present in the JSON body,
     * even when it is sent with {@code null}.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
        this.statusPresent = true;
    }

    /** @return {@code true} when the status attribute was present in the request */
    @JsonIgnore
    public boolean isStatusPresent() { return statusPresent; }

    /** @return the notes */
    public String getSamplingNotes() { return samplingNotes; }

    /** @param samplingNotes the notes */
    public void setSamplingNotes(String samplingNotes) { this.samplingNotes = samplingNotes; }
}
