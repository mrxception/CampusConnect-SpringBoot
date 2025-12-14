package com.cituconnect.service;

import com.cituconnect.entity.Notification;
import com.cituconnect.entity.User;
import com.cituconnect.repository.NotificationRepository;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired private NotificationRepository notificationRepository;
    @Autowired private UserRepository userRepository;

    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserUserIdAndIsReadFalse(userId);
    }

    public void markAsRead(Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
        });
    }

    public void markAllAsRead(Long userId) {
        List<Notification> list = notificationRepository.findByUserUserIdOrderByCreatedAtDesc(userId);
        for (Notification n : list) {
            if (!Boolean.TRUE.equals(n.getIsRead())) {
                n.setIsRead(true);
            }
        }
        notificationRepository.saveAll(list);
    }

    public void createNotification(Long recipientId, String title, String message, String type, Long relatedItemId) {
        User recipient = userRepository.findById(recipientId).orElse(null);

        if (recipient != null) {
            Notification n = Notification.builder()
                    .user(recipient)
                    .title(title)
                    .message(message)
                    .type(type)
                    .relatedItemId(relatedItemId)
                    .isRead(false)
                    .isUrgent(false)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(n);
        }
    }
}