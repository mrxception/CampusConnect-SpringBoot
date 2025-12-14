package com.cituconnect.service;

import com.cituconnect.entity.Message;
import com.cituconnect.entity.User;
import com.cituconnect.repository.MessageRepository;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired private MessageRepository messageRepository;
    @Autowired private UserRepository userRepository;

    public Message sendMessage(Long senderId, Long recipientId, String content) throws Exception {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new Exception("Sender not found"));
        User receiver = userRepository.findById(recipientId).orElseThrow(() -> new Exception("Receiver not found"));

        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(content)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    public List<Map<String, Object>> getChatHistory(Long currentUserId, Long otherUserId) {
        List<Message> messages = messageRepository.findChatHistory(currentUserId, otherUserId);

        boolean updated = false;
        for (Message m : messages) {
            if (m.getReceiver().getUserId().equals(currentUserId) && !Boolean.TRUE.equals(m.getIsRead())) {
                m.setIsRead(true);
                updated = true;
            }
        }
        if (updated) messageRepository.saveAll(messages);

        return messages.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", m.getId().toString());
            map.put("senderId", m.getSender().getUserId().toString());
            map.put("senderName", m.getSender().getName());
            map.put("content", m.getContent());
            map.put("timestamp", m.getCreatedAt().toString());
            map.put("isRead", m.getIsRead());
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getConversations(Long currentUserId) {
        List<Message> allMessages = messageRepository.findAllMessagesByUser(currentUserId);
        List<Map<String, Object>> conversations = new ArrayList<>();

        if (allMessages == null || allMessages.isEmpty()) {
            return conversations;
        }

        Map<Long, Message> lastMessageMap = new LinkedHashMap<>();

        for (Message m : allMessages) {
            if (m.getSender() == null || m.getReceiver() == null) continue;

            Long otherId = m.getSender().getUserId().equals(currentUserId)
                    ? m.getReceiver().getUserId()
                    : m.getSender().getUserId();

            if (!lastMessageMap.containsKey(otherId)) {
                lastMessageMap.put(otherId, m);
            }
        }

        for (Map.Entry<Long, Message> entry : lastMessageMap.entrySet()) {
            Long otherId = entry.getKey();
            Message latestMsg = entry.getValue();

            User otherUser = latestMsg.getSender().getUserId().equals(otherId)
                    ? latestMsg.getSender()
                    : latestMsg.getReceiver();

            long unread = messageRepository.countUnreadFromUser(otherId, currentUserId);

            Map<String, Object> conv = new HashMap<>();
            conv.put("id", otherId.toString());
            conv.put("participantId", otherId.toString());
            conv.put("participantName", otherUser.getName());
            conv.put("participantAvatar", otherUser.getProfileImageUrl());
            conv.put("lastMessage", latestMsg.getContent());
            conv.put("lastMessageTime", latestMsg.getCreatedAt().toString());
            conv.put("unreadCount", unread);

            conversations.add(conv);
        }

        return conversations;
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public long getTotalUnreadCount(Long userId) {
        return messageRepository.countByReceiverUserIdAndIsReadFalse(userId);
    }
}