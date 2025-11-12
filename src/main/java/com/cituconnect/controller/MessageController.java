package com.cituconnect.controller;

import com.cituconnect.entity.Message;
import com.cituconnect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<?> sendMessage(@RequestBody Message message) {
        Message sentMessage = messageService.sendMessage(message);
        return ResponseEntity.ok(Map.of("message", "Message sent successfully", "data", sentMessage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        if (message.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/conversation/{senderId}/{receiverId}")
    public ResponseEntity<?> getConversation(@PathVariable Long senderId, @PathVariable Long receiverId) {
        List<Message> messages = messageService.getConversation(senderId, receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/user/{receiverId}")
    public ResponseEntity<?> getMessagesForUser(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getMessagesForUser(receiverId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/unread/{receiverId}")
    public ResponseEntity<?> getUnreadMessages(@PathVariable Long receiverId) {
        List<Message> messages = messageService.getUnreadMessages(receiverId);
        return ResponseEntity.ok(messages);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable Long id) {
        Message message = messageService.markAsRead(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Message marked as read", "data", message));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok(Map.of("message", "Message deleted successfully"));
    }
}
