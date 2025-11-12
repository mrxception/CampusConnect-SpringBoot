package com.cituconnect.repository;

import com.cituconnect.entity.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    List<Discussion> findByForumId(Long forumId);
    List<Discussion> findByUserUserId(Long userId);

    List<Discussion> findByForumIdOrderByCreatedAtDesc(Long forumId);
}
