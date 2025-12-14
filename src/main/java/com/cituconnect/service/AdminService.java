package com.cituconnect.service;

import com.cituconnect.entity.*;
import com.cituconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ForumRepository forumRepository;
    
    @Autowired
    private DiscussionRepository discussionRepository;
    
    @Autowired
    private LostFoundRepository lostFoundRepository;
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private NotificationRepository notificationRepository;
    
    @Autowired
    private DiscussionLikeRepository discussionLikeRepository;
    
    @Autowired
    private ForumLikeRepository forumLikeRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    // ==================== USER MANAGEMENT ====================
    
    /**
     * Get all users in the system
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Get a specific user by ID
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    /**
     * Promote a user to admin role
     */
    public User promoteUserToAdmin(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setRole("ADMIN");
        return userRepository.save(user);
    }
    
    /**
     * Demote an admin user to regular user
     */
    public User demoteAdminToUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setRole("USER");
        return userRepository.save(user);
    }
    
    /**
     * Activate a user account
     */
    public User activateUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsActive(true);
        return userRepository.save(user);
    }
    
    /**
     * Deactivate a user account
     */
    public User deactivateUser(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setIsActive(false);
        return userRepository.save(user);
    }
    
    /**
     * Delete a user account
     */
    public void deleteUser(Long userId) throws Exception {
        if (!userRepository.existsById(userId)) {
            throw new Exception("User not found");
        }
        userRepository.deleteById(userId);
    }
    
    /**
     * Reset a user's password
     */
    public User resetUserPassword(Long userId, String newPassword) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
    
    /**
     * Get admin statistics
     */
    public AdminStatistics getStatistics() {
        return AdminStatistics.builder()
                .totalUsers(userRepository.count())
                .activeUsers(userRepository.findAll().stream()
                        .filter(User::getIsActive).count())
                .totalForums(forumRepository.count())
                .totalDiscussions(discussionRepository.count())
                .totalLostFoundPosts(lostFoundRepository.count())
                .totalMessages(messageRepository.count())
                .totalNotifications(notificationRepository.count())
                .build();
    }
    
    // ==================== FORUM MANAGEMENT ====================
    
    /**
     * Get all forums
     */
    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }
    
    /**
     * Delete a forum by ID
     */
    public void deleteForum(Long forumId) throws Exception {
        if (!forumRepository.existsById(forumId)) {
            throw new Exception("Forum not found");
        }
        forumRepository.deleteById(forumId);
    }
    
    /**
     * Get a specific forum by ID
     */
    public Optional<Forum> getForumById(Long forumId) {
        return forumRepository.findById(forumId);
    }
    
    // ==================== DISCUSSION MANAGEMENT ====================
    
    /**
     * Get all discussions
     */
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }
    
    /**
     * Delete a discussion by ID
     */
    public void deleteDiscussion(Long discussionId) throws Exception {
        if (!discussionRepository.existsById(discussionId)) {
            throw new Exception("Discussion not found");
        }
        discussionRepository.deleteById(discussionId);
    }
    
    /**
     * Get a specific discussion by ID
     */
    public Optional<Discussion> getDiscussionById(Long discussionId) {
        return discussionRepository.findById(discussionId);
    }
    
    // ==================== LOST & FOUND MANAGEMENT ====================
    
    /**
     * Get all lost and found posts
     */
    public List<LostFound> getAllLostFoundPosts() {
        return lostFoundRepository.findAll();
    }
    
    /**
     * Delete a lost and found post by ID
     */
    public void deleteLostFoundPost(Long postId) throws Exception {
        if (!lostFoundRepository.existsById(postId)) {
            throw new Exception("Lost and found post not found");
        }
        lostFoundRepository.deleteById(postId);
    }
    
    /**
     * Get a specific lost and found post by ID
     */
    public Optional<LostFound> getLostFoundPostById(Long postId) {
        return lostFoundRepository.findById(postId);
    }
    
    // ==================== MESSAGE MANAGEMENT ====================
    
    /**
     * Get all messages
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    
    /**
     * Delete a message by ID
     */
    public void deleteMessage(Long messageId) throws Exception {
        if (!messageRepository.existsById(messageId)) {
            throw new Exception("Message not found");
        }
        messageRepository.deleteById(messageId);
    }
    
    // ==================== NOTIFICATION MANAGEMENT ====================
    
    /**
     * Get all notifications
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    
    /**
     * Delete a notification by ID
     */
    public void deleteNotification(Long notificationId) throws Exception {
        if (!notificationRepository.existsById(notificationId)) {
            throw new Exception("Notification not found");
        }
        notificationRepository.deleteById(notificationId);
    }
    
    // ==================== MODERATION ====================
    
    /**
     * Check if a user is an admin
     */
    public boolean isAdmin(User user) {
        return user != null && "ADMIN".equals(user.getRole());
    }
    
    /**
     * Get all admin users
     */
    public List<User> getAllAdmins() {
        return userRepository.findAll().stream()
                .filter(user -> "ADMIN".equals(user.getRole()))
                .toList();
    }
}
