package com.cituconnect.service;

import com.cituconnect.entity.Message;
import com.cituconnect.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        message.setIsRead(false);
        return messageRepository.save(message);
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public List<Message> getConversation(Long senderId, Long receiverId) {
        return messageRepository.findBySenderUserIdAndReceiverUserId(senderId, receiverId);
    }

    public List<Message> getMessagesForUser(Long receiverId) {
        return messageRepository.findByReceiverUserId(receiverId);
    }

    public List<Message> getUnreadMessages(Long receiverId) {
        return messageRepository.findByReceiverUserIdAndIsReadFalse(receiverId);
    }

    public Message markAsRead(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            Message m = message.get();
            m.setIsRead(true);
            return messageRepository.save(m);
        }
        return null;
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
