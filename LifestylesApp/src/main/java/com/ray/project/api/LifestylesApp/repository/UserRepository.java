package com.ray.project.api.LifestylesApp.repository;

import com.ray.project.api.LifestylesApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user_app WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
}
