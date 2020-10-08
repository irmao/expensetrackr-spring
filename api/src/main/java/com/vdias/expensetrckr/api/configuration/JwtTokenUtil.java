package com.vdias.expensetrckr.api.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@PropertySource(value = {"classpath:application.properties"})
@Component
public class JwtTokenUtil implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -723196038650185510L;

    /**
     * JWT Secret. Read from properties.
     */
    @Value("${app.auth.jwt.secret}")
    private String secret;

    /**
     * JWT expiration time. Read from properties.
     */
    @Value("${app.auth.jwt.expirationTime}")
    private long expirationTime;

    /**
     * Gets the Username property from the token.
     *
     * @param token the token
     * @return the property
     */
    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Gets the Issued At property from the token.
     *
     * @param token the token
     * @return the property
     */
    public Date getIssuedAtDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    /**
     * Gets the Expiration Date property from the token.
     *
     * @param token the token
     * @return the property
     */
    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Applies a function, given as argument, to retrieve an specific claim from the token.
     *
     * @param <T>            the type fo the claim
     * @param token          the token
     * @param claimsResolver the resolver function that will retrieve the claim
     * @return the claim
     */
    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a token from the given user details.
     *
     * @param userDetails the user details
     * @return the generated token
     */
    public String generateToken(final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Informs whether the given token can be refreshed. It can be refreshed if it is
     * not expired or the token expiration is ignored.
     *
     * @param token the token
     * @return an indication that it can be refreshed or not.
     */
    public boolean canTokenBeRefreshed(final String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    /**
     * Informs whether a token is valid, but checking if it is expired or the username in
     * the token matches the given user details.
     *
     * @param token       the token
     * @param userDetails the user details
     * @return whether the token is valid or not
     */
    public boolean isTokenValid(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Informs whether the expiration of the given token should be ignored or not.
     * If true, {@link #canTokenBeRefreshed(String)} will return true for the
     * token regardless the expiration date. In this implementation, the method
     * always returns false. Override it to change.
     *
     * @param token the token
     * @return false
     */
    protected boolean ignoreTokenExpiration(final String token) {
        return false;
    }

    /**
     * Generates and returns the token using the given claims and subject.
     *
     * @param claims  the claims
     * @param subject the subject
     * @return the generated token
     */
    protected String doGenerateToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Gets all the claims from the given token.
     *
     * @param token the token
     * @return the claims
     */
    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Informs whether the given token is expired.
     *
     * @param token the token
     * @return a flag indicating if it is expired or not
     */
    private boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
