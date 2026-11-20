package com.daimlertruck.platform.u20241e281.aftersales.application.commandservices;

import com.daimlertruck.platform.u20241e281.aftersales.application.commands.CreateAuthorizedWorkshopCommand;
import com.daimlertruck.platform.u20241e281.aftersales.domain.model.aggregates.AuthorizedWorkshop;
import com.daimlertruck.platform.u20241e281.shared.application.result.ApplicationError;
import com.daimlertruck.platform.u20241e281.shared.application.result.Result;

/**
 * Command service for the {@link AuthorizedWorkshop} aggregate.
 *
 * @author José Antonio Santana Luna
 */
public interface AuthorizedWorkshopCommandService {

    /**
     * Handles the creation of an authorized workshop.
     *
     * @param command the {@link CreateAuthorizedWorkshopCommand}
     * @return a {@link Result} with the created {@link AuthorizedWorkshop} on success,
     *         or an {@link ApplicationError} on validation or business rule failure
     */
    Result<AuthorizedWorkshop, ApplicationError> handle(CreateAuthorizedWorkshopCommand command);
}
