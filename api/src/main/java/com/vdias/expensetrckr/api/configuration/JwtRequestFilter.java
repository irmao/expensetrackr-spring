package com.vdias.expensetrckr.api.configuration;

import com.vdias.expensetrckr.domain.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter class responsible to validate a JWT Token in the http request.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    /**
     * Authorization header name. Read from properties.
     */
    @Value("${app.auth.jwt.authorizationHeader}")
    private String authorizationHeader;

    /**
     * Authorization token prefix. Read from properties.
     */
    @Value("${app.auth.jwt.tokenPrefix}")
    private String tokenPrefix;

    /**
     * JWT Token util.
     */
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Authentication service.
     */
    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Do the filter. Extracts the token from the request header, and then the username from the token, and checks
     * if valid.
     *
     * @param request     the http request
     * @param response    the http response
     * @param filterChain the filter chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(authorizationHeader);

        String extractedUsername = null;
        String jwtToken = null;

        // checks if the token is valid and extracts the username from it.
        if (requestTokenHeader != null && requestTokenHeader.startsWith(tokenPrefix + " ")) {
            // removes the token prefix
            jwtToken = requestTokenHeader.replace(tokenPrefix + " ", "");
            try {
                extractedUsername = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.info("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.info("Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer string");
        }

        // if the token is valid and the authentication object is not set on the context, sets it.
        if (extractedUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = authenticationService.loadUserByUsername(extractedUsername);

            if (jwtTokenUtil.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
