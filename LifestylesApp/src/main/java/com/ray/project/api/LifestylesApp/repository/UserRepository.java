package com.ray.project.api.LifestylesApp.repository;

import com.ray.project.api.LifestylesApp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByActiveToken(String activeToken);
    @Query(value = "SELECT * FROM user_app WHERE username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE user_app SET active_token = ?1 WHERE username = ?2", nativeQuery = true)
    void updateUserActiveToken(String activeToken, String username);

    @Modifying
    @Query(value = "UPDATE user_app SET password = ?1, email = ?2 WHERE username = ?3", nativeQuery = true)
    void updateUserPasswordAndEmail(String password, String email, String username);

    @Modifying
    @Query(value = "UPDATE user_app SET password = ?1 WHERE username = ?2", nativeQuery = true)
    void updateUserPassword(String password, String username);

    @Modifying
    @Query(value = "UPDATE user_app SET email = ?1 WHERE username = ?2", nativeQuery = true)
    void updateUserEmail(String email, String username);

    @Modifying
    @Query(value = "DELETE FROM user_app WHERE id = ?1 AND username = ?2", nativeQuery = true)
    void deleteByIdAndUsername(Integer id, String username);
}
