package com.cituconnect.controller;

import com.cituconnect.entity.*;
import com.cituconnect.service.AdminService;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    // ==================== DASHBOARD ====================
    
    /**
     * Get admin dashboard statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            AdminStatistics stats = adminService.getStatistics();
            return ResponseEntity.ok(Map.of("data", stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== USER MANAGEMENT ====================
    
    /**
     * Get all users
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<User> users = adminService.getAllUsers();
            return ResponseEntity.ok(Map.of("data", users));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Get a specific user by ID
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId,
                                          @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            Optional<User> user = adminService.getUserById(userId);
            if (user.isPresent()) {
                return ResponseEntity.ok(Map.of("data", user.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Promote a user to admin
     */
    @PutMapping("/users/{userId}/promote")
    public ResponseEntity<?> promoteUserToAdmin(@PathVariable Long userId,
                                                 @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            User promotedUser = adminService.promoteUserToAdmin(userId);
            return ResponseEntity.ok(Map.of("message", "User promoted to admin", "user", promotedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Demote an admin user to regular user
     */
    @PutMapping("/users/{userId}/demote")
    public ResponseEntity<?> demoteAdminToUser(@PathVariable Long userId,
                                                @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            User demotedUser = adminService.demoteAdminToUser(userId);
            return ResponseEntity.ok(Map.of("message", "Admin demoted to user", "user", demotedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Activate a user account
     */
    @PutMapping("/users/{userId}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long userId,
                                          @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            User activatedUser = adminService.activateUser(userId);
            return ResponseEntity.ok(Map.of("message", "User activated", "user", activatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Deactivate a user account
     */
    @PutMapping("/users/{userId}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long userId,
                                            @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            User deactivatedUser = adminService.deactivateUser(userId);
            return ResponseEntity.ok(Map.of("message", "User deactivated", "user", deactivatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a user
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId,
                                        @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteUser(userId);
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Reset a user's password
     */
    @PutMapping("/users/{userId}/reset-password")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long userId,
                                               @RequestBody Map<String, String> request,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            String newPassword = request.get("newPassword");
            if (newPassword == null || newPassword.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "New password is required"));
            }
            User user = adminService.resetUserPassword(userId, newPassword);
            return ResponseEntity.ok(Map.of("message", "Password reset successfully", "user", user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Get all admin users
     */
    @GetMapping("/users/role/admin")
    public ResponseEntity<?> getAllAdmins(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<User> admins = adminService.getAllAdmins();
            return ResponseEntity.ok(Map.of("data", admins));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== FORUM MANAGEMENT ====================
    
    /**
     * Get all forums
     */
    @GetMapping("/forums")
    public ResponseEntity<?> getAllForums(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<Forum> forums = adminService.getAllForums();
            return ResponseEntity.ok(Map.of("data", forums));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a forum
     */
    @DeleteMapping("/forums/{forumId}")
    public ResponseEntity<?> deleteForum(@PathVariable Long forumId,
                                         @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteForum(forumId);
            return ResponseEntity.ok(Map.of("message", "Forum deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Get a specific forum by ID
     */
    @GetMapping("/forums/{forumId}")
    public ResponseEntity<?> getForumById(@PathVariable Long forumId,
                                          @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            Optional<Forum> forum = adminService.getForumById(forumId);
            if (forum.isPresent()) {
                return ResponseEntity.ok(Map.of("data", forum.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== DISCUSSION MANAGEMENT ====================
    
    /**
     * Get all discussions
     */
    @GetMapping("/discussions")
    public ResponseEntity<?> getAllDiscussions(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<Discussion> discussions = adminService.getAllDiscussions();
            return ResponseEntity.ok(Map.of("data", discussions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a discussion
     */
    @DeleteMapping("/discussions/{discussionId}")
    public ResponseEntity<?> deleteDiscussion(@PathVariable Long discussionId,
                                              @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteDiscussion(discussionId);
            return ResponseEntity.ok(Map.of("message", "Discussion deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Get a specific discussion by ID
     */
    @GetMapping("/discussions/{discussionId}")
    public ResponseEntity<?> getDiscussionById(@PathVariable Long discussionId,
                                               @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            Optional<Discussion> discussion = adminService.getDiscussionById(discussionId);
            if (discussion.isPresent()) {
                return ResponseEntity.ok(Map.of("data", discussion.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== LOST & FOUND MANAGEMENT ====================
    
    /**
     * Get all lost and found posts
     */
    @GetMapping("/lost-found")
    public ResponseEntity<?> getAllLostFoundPosts(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<LostFound> posts = adminService.getAllLostFoundPosts();
            return ResponseEntity.ok(Map.of("data", posts));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a lost and found post
     */
    @DeleteMapping("/lost-found/{postId}")
    public ResponseEntity<?> deleteLostFoundPost(@PathVariable Long postId,
                                                 @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteLostFoundPost(postId);
            return ResponseEntity.ok(Map.of("message", "Lost and found post deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Get a specific lost and found post by ID
     */
    @GetMapping("/lost-found/{postId}")
    public ResponseEntity<?> getLostFoundPostById(@PathVariable Long postId,
                                                  @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            Optional<LostFound> post = adminService.getLostFoundPostById(postId);
            if (post.isPresent()) {
                return ResponseEntity.ok(Map.of("data", post.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== MESSAGE MANAGEMENT ====================
    
    /**
     * Get all messages
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<Message> messages = adminService.getAllMessages();
            return ResponseEntity.ok(Map.of("data", messages));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a message
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long messageId,
                                           @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteMessage(messageId);
            return ResponseEntity.ok(Map.of("message", "Message deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== NOTIFICATION MANAGEMENT ====================
    
    /**
     * Get all notifications
     */
    @GetMapping("/notifications")
    public ResponseEntity<?> getAllNotifications(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            List<Notification> notifications = adminService.getAllNotifications();
            return ResponseEntity.ok(Map.of("data", notifications));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Delete a notification
     */
    @DeleteMapping("/notifications/{notificationId}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long notificationId,
                                                @RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (!isAdmin(token)) {
                return ResponseEntity.status(403).body(Map.of("error", "Admin access required"));
            }
            adminService.deleteNotification(notificationId);
            return ResponseEntity.ok(Map.of("message", "Notification deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Check if the requesting user is an admin
     */
    private boolean isAdmin(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }
        try {
            String email = jwtTokenUtil.getEmailFromToken(token.replace("Bearer ", ""));
            User user = userService.findByEmail(email);
            return adminService.isAdmin(user);
        } catch (Exception e) {
            return false;
        }
    }
}
