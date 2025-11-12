package com.cituconnect.repository;

import com.cituconnect.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderUserIdAndReceiverUserId(Long senderId, Long receiverId);
    List<Message> findByReceiverUserId(Long receiverId);
    List<Message> findByReceiverUserIdAndIsReadFalse(Long receiverId);
}
