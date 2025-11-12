package com.cituconnect.repository;

import com.cituconnect.entity.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    List<Forum> findByCategory(String category);
    List<Forum> findByUserUserId(Long userId);
    List<Forum> findByTitleContainingIgnoreCase(String title);
    List<Forum> findAllByOrderByCreatedAtDesc();
}
