package com.vdias.expensetrckr.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static com.vdias.expensetrckr.model.UserState.INACTIVE;
import static com.vdias.expensetrckr.model.UserState.REQUIRES_PASSWORD_CHANGE;
import static java.util.Arrays.asList;

public class AuthenticatedUser implements UserDetails {
    private User user;

    public AuthenticatedUser(@NotNull final User pUser) {
        this.user = pUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return asList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getState() != REQUIRES_PASSWORD_CHANGE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getState() != INACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getState() != REQUIRES_PASSWORD_CHANGE;
    }

    @Override
    public boolean isEnabled() {
        return user.getState() != INACTIVE;
    }
}
