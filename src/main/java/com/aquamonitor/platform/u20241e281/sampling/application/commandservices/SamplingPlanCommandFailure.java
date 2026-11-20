package com.aquamonitor.platform.u20241e281.sampling.application.commandservices;

import java.util.List;

/**
 * Failure types of the sampling plan commands. Each one exposes an i18n message key and its arguments.
 */
public sealed interface SamplingPlanCommandFailure permits SamplingPlanCommandFailure.InvalidData,
        SamplingPlanCommandFailure.DuplicatePlanCode, SamplingPlanCommandFailure.Unexpected {

    /**
     * Gets the i18n message key of the failure.
     *
     * @return the message key
     */
    String messageKey();

    /**
     * Gets the arguments used to format the message.
     *
     * @return the message arguments
     */
    List<Object> messageArguments();

    /**
     * The data violates a business rule of the domain.
     *
     * @param reason the explanatory (English) reason
     */
    record InvalidData(String reason) implements SamplingPlanCommandFailure {
        @Override
        public String messageKey() {
            return "error.request.invalid-data";
        }

        @Override
        public List<Object> messageArguments() {
            return List.of(reason == null ? "" : reason);
        }
    }

    /**
     * Another sampling plan already uses the plan code.
     *
     * @param planCode the duplicated plan code
     */
    record DuplicatePlanCode(String planCode) implements SamplingPlanCommandFailure {
        @Override
        public String messageKey() {
            return "sampling-plan.plan-code.duplicated";
        }

        @Override
        public List<Object> messageArguments() {
            return List.of(planCode);
        }
    }

    /**
     * An unexpected error occurred.
     */
    record Unexpected() implements SamplingPlanCommandFailure {
        @Override
        public String messageKey() {
            return "error.unexpected";
        }

        @Override
        public List<Object> messageArguments() {
            return List.of();
        }
    }
}
