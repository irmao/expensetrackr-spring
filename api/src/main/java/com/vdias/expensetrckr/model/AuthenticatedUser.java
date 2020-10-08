package com.vdias.expensetrckr.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static com.vdias.expensetrckr.model.UserState.INACTIVE;
import static com.vdias.expensetrckr.model.UserState.REQUIRES_PASSWORD_CHANGE;
import static java.util.Arrays.asList;

public class AuthenticatedUser implements UserDetails {
    private String username;
    private String password;
    private UserState state;

    public AuthenticatedUser(@NotNull final User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.state = user.getState();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return asList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return state != REQUIRES_PASSWORD_CHANGE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return state != INACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return state != REQUIRES_PASSWORD_CHANGE;
    }

    @Override
    public boolean isEnabled() {
        return state != INACTIVE;
    }
}
