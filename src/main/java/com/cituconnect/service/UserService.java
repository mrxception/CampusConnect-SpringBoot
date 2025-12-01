package com.cituconnect.service;

import com.cituconnect.entity.User;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String name, String email, String password) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .dateRegistered(LocalDateTime.now())
                .lastActive(LocalDateTime.now())
                .isActive(true)
                .build();
        return userRepository.save(user);
    }

    public User createAdminUser(String name, String email, String password) {
        User user = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("ADMIN")
                .dateRegistered(LocalDateTime.now())
                .lastActive(LocalDateTime.now())
                .isActive(true)
                .build();
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long userId, User userDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (userDetails.getName() != null) existingUser.setName(userDetails.getName());
            if (userDetails.getCourse() != null) existingUser.setCourse(userDetails.getCourse());
            if (userDetails.getYearLevel() != null) existingUser.setYearLevel(userDetails.getYearLevel());
            if (userDetails.getStudentId() != null) existingUser.setStudentId(userDetails.getStudentId());
            if (userDetails.getBio() != null) existingUser.setBio(userDetails.getBio());
            // Optional: Add logic to allow updating role if authenticated as admin
            // if (userDetails.getRole() != null) existingUser.setRole(userDetails.getRole());
            existingUser.setLastActive(LocalDateTime.now());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}