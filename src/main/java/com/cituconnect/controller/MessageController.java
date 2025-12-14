package com.cituconnect.controller;

import com.cituconnect.entity.Message;
import com.cituconnect.entity.User;
import com.cituconnect.repository.MessageRepository;
import com.cituconnect.repository.UserRepository;
import com.cituconnect.service.MessageService;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class MessageController {

    @Autowired private MessageService messageService;
    @Autowired private UserService userService;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private MessageRepository messageRepository;
    @Autowired private UserRepository userRepository;

    private Long getUserIdFromToken(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            System.out.println("❌ Auth Header is NULL or Empty");
            return null;
        }

        String token = authHeader.replaceFirst("(?i)Bearer ", "").trim();

        System.out.println("👉 processing token: [" + token + "]");

        String email = jwtTokenUtil.getEmailFromToken(token);
        if (email == null) {
            System.out.println("❌ Could not extract email from token.");
            return null;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            System.out.println("❌ User not found for email: " + email);
            return null;
        }

        return user.getUserId();
    }

    @GetMapping("/conversations")
    public ResponseEntity<?> getConversations(@RequestHeader(value = "Authorization", required = false) String token) {
        Long currentUserId = getUserIdFromToken(token);
        if (currentUserId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));

        return ResponseEntity.ok(Map.of("data", messageService.getConversations(currentUserId)));
    }

    @GetMapping("/{otherUserId}")
    public ResponseEntity<?> getMessages(@PathVariable Long otherUserId,
                                         @RequestHeader(value = "Authorization", required = false) String token) {
        Long currentUserId = getUserIdFromToken(token);
        if (currentUserId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));

        return ResponseEntity.ok(Map.of("data", messageService.getChatHistory(currentUserId, otherUserId)));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, Object> request,
                                         @RequestHeader(value = "Authorization", required = false) String token) {
        Long currentUserId = getUserIdFromToken(token);
        if (currentUserId == null) return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));

        try {
            Object recipientObj = request.get("recipientId");
            Long recipientId = recipientObj instanceof Integer ? ((Integer) recipientObj).longValue() : Long.valueOf(recipientObj.toString());

            String content = (String) request.get("content");

            messageService.sendMessage(currentUserId, recipientId, content);
            return ResponseEntity.ok(Map.of("message", "Sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(Map.of("message", "Deleted"));
    }

    @GetMapping
    public ResponseEntity<?> getAllMessages() {
        return ResponseEntity.ok(Map.of("data", messageService.getAllMessages()));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<?> getUnreadCount(@RequestHeader(value = "Authorization", required = false) String token) {
        Long currentUserId = getUserIdFromToken(token);
        if (currentUserId == null) return ResponseEntity.ok(Map.of("count", 0));

        long count = messageService.getTotalUnreadCount(currentUserId);
        return ResponseEntity.ok(Map.of("count", count));
    }
}