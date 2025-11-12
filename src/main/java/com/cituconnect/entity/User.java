package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String course;
    private String yearLevel;
    private String studentId;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String profileImageUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateRegistered = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime lastActive = LocalDateTime.now();

    private Boolean isActive = true;
}
