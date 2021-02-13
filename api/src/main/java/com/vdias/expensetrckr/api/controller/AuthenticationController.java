package com.vdias.expensetrckr.api.controller;

import com.vdias.expensetrckr.api.dto.UserCredentials;
import com.vdias.expensetrckr.domain.service.AuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * API Controller class that expose endpoints related to the authentication process.
 */
@RestController
@Validated
@RequestMapping(AuthenticationController.API_ENDPOINT)
@Api("Provides operations regarding authentication")
public class AuthenticationController {

    /**
     * The REST API endpoint related to this controller.
     */
    public static final String API_ENDPOINT = "/authentication";

    /**
     * Instance of {@link AuthenticationService} class.
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Authenticates an user with the given credentials.
     *
     * @param credentials the user credentials
     */
    @PostMapping("/credentials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Authenticates an user")
    public void authenticate(@Valid @NotNull @RequestBody final UserCredentials credentials) {
        authenticationService.performLogin(credentials);
    }
}
