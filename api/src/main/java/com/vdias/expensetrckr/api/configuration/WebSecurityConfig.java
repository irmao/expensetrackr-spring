package com.vdias.expensetrckr.api.configuration;

import com.vdias.expensetrckr.domain.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * List of allowed endpoints.
     */
    private static final String[] AUTH_ALLOWED_LIST = {
            // -- h2 console
            "/h2-console/**",
            // -- swagger ui
            "/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            // -- API endpoints
            "/users/login"
    };

    /**
     * Authentication service.
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * JWT Request filter.
     */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * Informs Spring that the passwordEncoder used in the application will be
     * the {@link BCryptPasswordEncoder}, and sets its configuration, if required.
     *
     * @return the password encoder used in the application
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides the AuthenticationManager bean to the entire application.
     *
     * @return an instance of the Authentication manager
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Informs Spring to use  to use the {@link AuthenticationService} as service for
     * AuthenticationManager and {@link #passwordEncoder()} as its password encoder.
     *
     * @param auth authentication manager builder
     * @throws Exception if anything goes wrong in the configuration
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService).passwordEncoder(passwordEncoder());
    }

    /**
     * Configures the Http Security.
     *
     * @param http the http to be configured
     * @throws Exception if anything goes wrong in the configuration
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                // allows frames from same origin (h2 console uses it)
                .headers().frameOptions().sameOrigin()
                // disables csrf
                .and().csrf().disable()
                .authorizeRequests()
                // allows a few endpoints to be authorized without authentication
                .antMatchers(AUTH_ALLOWED_LIST).permitAll()
                // all other requests need to be authenticated
                .anyRequest().authenticated()
                // responds the status UNAUTHORIZED when an exception has occurred previously in the filter
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                // sets the session as STATELESS, so session won't be used to store the user's state
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // adds the JWT filter to validate to token with every request
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
