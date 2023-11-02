package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    User getByEmail(String email);

    Optional<User> findByEmail(String email);
    User findByUsertoken(String usertoken);
}
