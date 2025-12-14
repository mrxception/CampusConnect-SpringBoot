package com.cituconnect.repository;

import com.cituconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.userId = :user1Id AND m.receiver.userId = :user2Id) " +
            "OR (m.sender.userId = :user2Id AND m.receiver.userId = :user1Id) " +
            "ORDER BY m.createdAt ASC")
    List<Message> findChatHistory(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

    @Query("SELECT m FROM Message m WHERE m.sender.userId = :userId OR m.receiver.userId = :userId ORDER BY m.createdAt DESC")
    List<Message> findAllMessagesByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.sender.userId = :senderId AND m.receiver.userId = :receiverId AND m.isRead = false")
    long countUnreadFromUser(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId);

    long countByReceiverUserIdAndIsReadFalse(Long receiverId);
}