package com.vdias.expensetrckr.repository;

import com.vdias.expensetrckr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository to retrieve and store data related to the {@link User} model.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds the user by their unique username.
     *
     * @param username the username
     * @return an optional with the found user, or empty()
     */
    Optional<User> findByUsername(String username);
}
