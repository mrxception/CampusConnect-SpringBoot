package com.cituconnect.service;

import com.cituconnect.entity.User;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.scheduling.annotation.Scheduled;

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
                .isOnline(false)
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
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (userDetails.getName() != null) user.setName(userDetails.getName());
            if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
            if (userDetails.getCourse() != null) user.setCourse(userDetails.getCourse());
            if (userDetails.getYearLevel() != null) user.setYearLevel(userDetails.getYearLevel());
            if (userDetails.getStudentId() != null) user.setStudentId(userDetails.getStudentId());
            if (userDetails.getBio() != null) user.setBio(userDetails.getBio());

            if (userDetails.getProfileImageUrl() != null) {
                user.setProfileImageUrl(userDetails.getProfileImageUrl());
            }

            if (userDetails.getRole() != null) user.setRole(userDetails.getRole());


            return userRepository.save(user);
        }
        return null;
    }

    public List<User> getAllActiveUsers() {
        return userRepository.findByIsOnlineTrue();
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void updateUserHeartbeat(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            user.setLastActive(LocalDateTime.now());
            user.setIsOnline(true);
            userRepository.save(user);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkAndSetOfflineUsers() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(5);

        List<User> users = userRepository.findByIsOnlineTrue();

        for (User user : users) {
            if (user.getLastActive() != null && user.getLastActive().isBefore(threshold)) {
                user.setIsOnline(false);
                userRepository.save(user);
                System.out.println("User " + user.getName() + " marked OFFLINE due to inactivity.");
            }
        }
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