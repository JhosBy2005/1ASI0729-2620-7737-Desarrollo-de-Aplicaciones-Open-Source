package com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest;

import com.daimlertruck.platform.u20241e281.aftersales.application.commandservices.AuthorizedWorkshopCommandService;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources.AuthorizedWorkshopResource;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.resources.CreateAuthorizedWorkshopResource;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.transform.AuthorizedWorkshopResourceFromEntityAssembler;
import com.daimlertruck.platform.u20241e281.aftersales.interfaces.rest.transform.CreateAuthorizedWorkshopCommandFromResourceAssembler;
import com.daimlertruck.platform.u20241e281.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the authorized workshops endpoint.
 *
 * @author José Antonio Santana Luna
 */
@RestController
@RequestMapping(value = "/api/v1/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Workshops", description = "Authorized workshop management endpoints")
public class AuthorizedWorkshopsController {

    private final AuthorizedWorkshopCommandService authorizedWorkshopCommandService;

    /**
     * Constructor.
     *
     * @param authorizedWorkshopCommandService the {@link AuthorizedWorkshopCommandService}
     */
    public AuthorizedWorkshopsController(AuthorizedWorkshopCommandService authorizedWorkshopCommandService) {
        this.authorizedWorkshopCommandService = authorizedWorkshopCommandService;
    }

    /**
     * Registers a new authorized workshop.
     *
     * @param resource the {@link CreateAuthorizedWorkshopResource} request body
     * @return 201 with the created {@link AuthorizedWorkshopResource}, or an error ProblemDetail
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add an authorized workshop",
            description = "Registers a new authorized workshop. The id and workshopId are generated automatically "
                    + "and the maintenance authorization code is stored encrypted with BCrypt.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Authorized workshop created",
                    content = @Content(schema = @Schema(implementation = AuthorizedWorkshopResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. invalid e-mail or phone number)"),
            @ApiResponse(responseCode = "409", description = "Workshop manager already registered for the same workshop legal name and country or region")
    })
    public ResponseEntity<?> createAuthorizedWorkshop(@Valid @RequestBody CreateAuthorizedWorkshopResource resource) {
        var command = CreateAuthorizedWorkshopCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = authorizedWorkshopCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                AuthorizedWorkshopResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}
