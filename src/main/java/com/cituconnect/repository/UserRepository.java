package com.cituconnect.repository;

import com.cituconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStudentId(String studentId);
    List<User> findByIsActiveTrue();
    Boolean existsByEmail(String email);
    List<User> findByIsOnlineTrue();
}