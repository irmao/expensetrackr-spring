package com.vdias.expensetrckr.domain.service;

import com.vdias.expensetrckr.api.configuration.JwtTokenUtil;
import com.vdias.expensetrckr.api.dto.UserCredentials;
import com.vdias.expensetrckr.domain.exception.AuthenticationException;
import com.vdias.expensetrckr.model.AuthenticatedUser;
import com.vdias.expensetrckr.model.User;
import com.vdias.expensetrckr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.vdias.expensetrckr.domain.exception.ApplicationExceptionReason.BAD_CREDENTIALS;
import static com.vdias.expensetrckr.domain.exception.ApplicationExceptionReason.INACTIVE_USER;

/**
 * Class that provide authentication services. It works as a connection between the Domain
 * layer of the application and the SpringBoot Security framework.
 */
@Service
public class AuthenticationService implements UserDetailsService {
    /**
     * The authentication manager.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * THe JWT Token Util.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Tries to perform login using the given credentials and, if successful, returns the authentication token.
     *
     * @param credentials the user credentials
     * @return the authentication token
     * @throws {@link AuthenticationException} if user is not allowed to perform login (inactive, wrong password, etc)
     */
    public String performLogin(final UserCredentials credentials) {
        authenticate(credentials);

        final UserDetails userDetails = loadUserByUsername(credentials.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     * Tries to authenticate the user using the given credentials. Throws an {@link AuthenticationException} if
     * the user couldn't be authenticated.
     *
     * @param credentials the credentials
     * @throws AuthenticationException if the user couldn't be authenticated
     */
    private void authenticate(final UserCredentials credentials) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword()));
        } catch (DisabledException e) {
            throw new AuthenticationException(INACTIVE_USER, e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(BAD_CREDENTIALS, e);
        }
    }

    /**
     * Gets an user by their username. Returns the UserDetails, if found, or throws an exception otherwise.
     *
     * @param username the username
     * @return the UserDetails. Will never be null.
     * @throws UsernameNotFoundException if the user was not found in the database
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
        return new AuthenticatedUser(user);
    }
}
