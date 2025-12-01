package com.cituconnect.repository;

import com.cituconnect.entity.ForumLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ForumLikeRepository extends JpaRepository<ForumLike, Long> {
    Optional<ForumLike> findByUserUserIdAndForumId(Long userId, Long forumId);
    Long countByForumId(Long forumId);
}