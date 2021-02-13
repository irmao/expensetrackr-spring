package com.vdias.expensetrckr.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity class that represent an expense.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserState state;

    public User() {
        // JPA requires default constructor.
    }

    public User(final long pId, final String pUsername, final String pPassword, final String pEmail, final UserState pState) {
        id = pId;
        username = pUsername;
        password = pPassword;
        email = pEmail;
        state = pState;
    }

    public long getId() {
        return id;
    }

    public void setId(final long pId) {
        id = pId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String pUsername) {
        username = pUsername;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String pPassword) {
        password = pPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String pEmail) {
        email = pEmail;
    }

    public UserState getState() {
        return state;
    }

    public void setState(final UserState pState) {
        state = pState;
    }
}
