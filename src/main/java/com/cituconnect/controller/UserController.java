333
delikado_
Invisible

Like — 3/25/25, 10:12 PM
Image
Image
Image
Image
Image
333 — 3/25/25, 10:14 PM
Image
Like — 3/25/25, 10:15 PM
Image
Image
Image
Image
Image
<ListView
        android:id="@+id/listView_Setttings"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
333 — 3/25/25, 10:17 PM
kani?
Like — 3/25/25, 10:18 PM
Image
DARA OHHHHHH
WALA NAY LABOTTT
Image
1
package com.android.fitfolio

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
Expand
message.txt
3 KB
2
package com.android.fitfolio.helper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.android.fitfolio.R
import com.android.fitfolio.data.Options

class SettingsAdapter (
    val context: Context,
    val optionsList: List<Options>,
    val onClickItem: (position: Int) -> Unit
): BaseAdapter() {
    override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View {
        val view = contentView ?: LayoutInflater.from(context).inflate(
            R.layout.settings_item,
            parent,
            false
        )

        val icon = view.findViewById<ImageView>(R.id.icon)
        val desc = view.findViewById<TextView>(R.id.desc)

        val options = optionsList[position]

        icon.setImageResource(options.icon)
        desc.setText(options.desc)

        view.setOnClickListener{
            onClickItem(position)
        }

        return view
    }

    override fun getCount(): Int = optionsList.size

    override fun getItem(position: Int): Any = optionsList[position]

    override fun getItemId(position: Int): Long = position.toLong()
}
 
3
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="10dp">
        <ImageView
            android:id="@+id/icon"
            android:layout_width="30dp"
            android:layout_height="30dp"
            android:layout_marginTop="10dp"/>
        <TextView
            android:id="@+id/desc"
            android:layout_marginLeft="10dp"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:text="About"
            android:layout_weight="1"
            android:textAllCaps="false"
            android:textStyle="bold"
            android:textAlignment="viewStart"
            android:background="@android:color/transparent"
            android:textColor="@color/black"
            android:textSize="20sp"/>

        <ImageView
            android:layout_width="30dp"
            android:layout_gravity="center_vertical"
            android:layout_height="30dp"
            android:background="@drawable/arrow"/>
    </LinearLayout>
    <View
        android:layout_width="match_parent"
        android:layout_height="3dp"
        android:layout_marginHorizontal="10dp"
        android:background="#909090"/>
</LinearLayout>
 
1 listview simple
package com.android.fitfolio

import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrackerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracker)
        val listView_Tracker = findViewById<ListView>(R.id.listView_Tracker)

        val listOfFrequentlyUsed = listOf(
            "TOP",
            "Online Payment - PAYMAYA",
            "Online Payment - VISA",
            "Online Payment - GOTYME",
            "Online Payment - CREDIT CARD"
        )

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listOfFrequentlyUsed
        )
        listView_Tracker.adapter = arrayAdapter

    }
}
 
2. list view simple
 val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listOfFrequentlyUsed
        )
        listView_Tracker.adapter = arrayAdapter
 
3 list view simpple

<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@android:id/text1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAppearance="?android:attr/textAppearanceListItemSmall"
    android:gravity="center_vertical"
    android:paddingStart="?android:attr/listPreferredItemPaddingStart"
    android:paddingEnd="?android:attr/listPreferredItemPaddingEnd"
    android:minHeight="?android:attr/listPreferredItemHeightSmall" />

built in
 
<ListView
        android:id="@+id/listView_Tracker"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
Like — 3/25/25, 11:26 PM
ilisi
333 — 3/25/25, 11:29 PM
kana about
Like — 3/25/25, 11:36 PM
oo
Dazzler — 4/9/25, 11:45 PM
smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * Game.SCALE) + rnd.nextInt((int) (100 * Game.SCALE));
Like — 11/12/25, 8:05 PM
https://www.kaggle.com/search?q=Philippine+in%3Adatasets
Search | Kaggle
Search for anything on Kaggle.
Image
https://www.kaggle.com/datasets/doyouevendata/kiva-phillipines-regional-info
Philippines Regional Demographic Info
specifically uploaded for kiva dataset analysis
Image
333 — 11/12/25, 9:19 PM
Image
Image
Dazzler — 11/12/25, 9:41 PM
package com.cituconnect.controller;

import com.cituconnect.entity.LostFound;
import com.cituconnect.service.LostFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
Expand
LostFoundController.java
4 KB
package com.cituconnect.controller;

import com.cituconnect.entity.Message;
import com.cituconnect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
Expand
MessageController.java
3 KB
package com.cituconnect.controller;

import com.cituconnect.entity.Notification;
import com.cituconnect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
Expand
NotificationController.java
4 KB
Image
package com.cituconnect.service;

import com.cituconnect.entity.Notification;
import com.cituconnect.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
Expand
NotificationService.java
2 KB
package com.cituconnect.service;

import com.cituconnect.entity.Message;
import com.cituconnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
Expand
MessageService.java
2 KB
package com.cituconnect.service;

import com.cituconnect.entity.LostFound;
import com.cituconnect.repository.LostFoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
Expand
LostFoundService.java
3 KB
package com.cituconnect.repository;

import com.cituconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
Expand
MessageRepository.java
1 KB
package com.cituconnect.repository;

import com.cituconnect.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
Expand
LostFoundRepository.java
1 KB
package com.cituconnect.repository;

import com.cituconnect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
Expand
NotificationRepository.java
1 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
Expand
Notification.java
1 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
Expand
LostFound.java
1 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
Expand
Message.java
1 KB
Image
Dazzler — 11/12/25, 9:48 PM
package com.cituconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
Expand
CampusconnectApplication.java
2 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
Expand
User.java
1 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
Expand
Like.java
1 KB
package com.cituconnect.controller;

import com.cituconnect.entity.User;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
Expand
UserController.java
5 KB
config
package com.cituconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
Expand
SecurityConfig.java
3 KB
package com.cituconnect.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
Expand
JwtAuthenticationFilter.java
3 KB
package com.cituconnect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
Expand
CorsConfig.java
1 KB
package com.cituconnect.service;

import com.cituconnect.entity.Like;
import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.User;
Expand
LikeService.java
4 KB
package com.cituconnect.service;

import com.cituconnect.entity.User;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
Expand
UserService.java
3 KB
package com.cituconnect.repository;

import com.cituconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
Expand
UserRepository.java
1 KB
package com.cituconnect.repository;

import com.cituconnect.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
Expand
LikeRepository.java
1 KB
package com.cituconnect.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
Expand
JwtTokenUtil.java
3 KB
git add src/main/java/com/cituconnect/controller/
Dazzler — 11/12/25, 10:06 PM
package com.cituconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CampusconnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusconnectApplication.class, args);
    }

}
git fetch origin
git reset --hard origin/main
Dazzler — 11/12/25, 10:33 PM
.
Like — 11/12/25, 10:59 PM
Image
https://www.figma.com/design/EWy38k20FofuI3uQUDsasH/Elective?node-id=0-1&p=f&t=yK4RHDY3jSCnV3qu-0
Like — 9:23 PM
laykel:
lost and found 
message
notification
333 — 9:24 PM
user 
like
config 
jwt
Dazzler — 9:26 PM
package com.cituconnect.controller;

import com.cituconnect.entity.Message;
import com.cituconnect.entity.User;
import com.cituconnect.repository.MessageRepository;
import com.cituconnect.repository.UserRepository;
Expand
MessageController.java
5 KB
package com.cituconnect.controller;

import com.cituconnect.entity.Notification;
import com.cituconnect.service.NotificationService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
Expand
NotificationController.java
3 KB
package com.cituconnect.repository;

import com.cituconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
Expand
MessageRepository.java
2 KB
package com.cituconnect.repository;

import com.cituconnect.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
Expand
NotificationRepository.java
1 KB
package com.cituconnect.service;

import com.cituconnect.entity.Message;
import com.cituconnect.entity.User;
import com.cituconnect.repository.MessageRepository;
import com.cituconnect.repository.UserRepository;
Expand
MessageService.java
5 KB
package com.cituconnect.service;

import com.cituconnect.entity.Notification;
import com.cituconnect.entity.User;
import com.cituconnect.repository.NotificationRepository;
import com.cituconnect.repository.UserRepository;
Expand
NotificationService.java
3 KB
package com.cituconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/users/**").permitAll()

                        .requestMatchers("/admin/**").permitAll()

                        .requestMatchers("/forum/**", "/discussion/**").permitAll()
                        .requestMatchers("/lost-found/**").permitAll()

                        .anyRequest().permitAll()
                )
                .httpBasic(basic -> basic.disable());

        return http.build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(new JwtInterceptor())
        //        .excludePathPatterns("/forum/*/like*", "/discussion/*/like*");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
Collapse
SecurityConfig.java
3 KB
package com.cituconnect.controller;

import com.cituconnect.entity.User;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String password = request.get("password");

            if (name == null || email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }

            if (!email.contains("@cit.edu")){
                return ResponseEntity.badRequest().body(Map.of("error", "Please use your CIT email address"));
            }

            User user = userService.createUser(name, email, password);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            User user = userService.findByEmail(email);
            if (user == null || !userService.validatePassword(password, user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
            }

            String token = jwtTokenUtil.generateToken(email, user.getUserId(), user.getName(), user.getRole());

            userService.updateUserHeartbeat(email);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "userId", user.getUserId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Server error during login", "detail", ex.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active/list")
... (59 lines left)
Collapse
UserController.java
7 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
Expand
DiscussionLike.java
1 KB
package com.cituconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
Expand
User.java
2 KB
package com.cituconnect.repository;

import com.cituconnect.entity.ForumLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
Expand
ForumLikeRepository.java
1 KB
package com.cituconnect.repository;

import com.cituconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
Expand
UserRepository.java
1 KB
package com.cituconnect.repository;

import com.cituconnect.entity.DiscussionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
Expand
DiscussionLikeRepository.java
1 KB
package com.cituconnect.service;

import com.cituconnect.entity.ForumLike;
import com.cituconnect.entity.DiscussionLike;
import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Discussion;
Expand
LikeService.java
5 KB
package com.cituconnect.service;

import com.cituconnect.entity.User;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
Expand
UserService.java
5 KB
﻿
package com.cituconnect.controller;

import com.cituconnect.entity.User;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String name = request.get("name");
            String email = request.get("email");
            String password = request.get("password");

            if (name == null || email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing required fields"));
            }

            if (!email.contains("@cit.edu")){
                return ResponseEntity.badRequest().body(Map.of("error", "Please use your CIT email address"));
            }

            User user = userService.createUser(name, email, password);
            return ResponseEntity.ok(Map.of("message", "User registered successfully", "user", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String password = request.get("password");

            User user = userService.findByEmail(email);
            if (user == null || !userService.validatePassword(password, user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
            }

            String token = jwtTokenUtil.generateToken(email, user.getUserId(), user.getName(), user.getRole());

            userService.updateUserHeartbeat(email);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "userId", user.getUserId(),
                    "name", user.getName(),
                    "email", user.getEmail(),
                    "role", user.getRole()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("message", "Server error during login", "detail", ex.getMessage()));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active/list")
    public ResponseEntity<?> getActiveUsers() {
        List<User> users = userService.getAllActiveUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String newRole = request.get("role");
        if (newRole == null || (!newRole.equals("USER") && !newRole.equals("ADMIN"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid role specified. Must be USER or ADMIN."));
        }

        User user = userOptional.get();
        user.setRole(newRole);
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(Map.of("message", "User role updated successfully", "user", updatedUser));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }

    @PostMapping("/heartbeat")
    public ResponseEntity<?> heartbeat(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            String jwt = token.substring(7);
            String email = jwtTokenUtil.getEmailFromToken(jwt);

            if (email != null) {
                userService.updateUserHeartbeat(email);
                return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}