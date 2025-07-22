package com.colvir.delivery.repository;

import com.colvir.delivery.model.User;
import com.colvir.delivery.service.impl.CustomUserDetailsService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String userName);

    @Query(value = """
        SELECT u.id, u.username, u.password, u.enabled FROM users u
    """,  nativeQuery = true)
    List<User> findAll();
}