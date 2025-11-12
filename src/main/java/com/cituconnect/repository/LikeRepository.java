package com.cituconnect.repository;

import com.cituconnect.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserUserIdAndForumId(Long userId, Long forumId);
    Optional<Like> findByUserUserIdAndDiscussionId(Long userId, Long discussionId);
    Long countByForumId(Long forumId);
    Long countByDiscussionId(Long discussionId);
    List<Like> findByForumId(Long forumId);
    List<Like> findByDiscussionId(Long discussionId);
}
