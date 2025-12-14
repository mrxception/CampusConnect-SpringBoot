package com.cituconnect.repository;

import com.cituconnect.entity.DiscussionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DiscussionLikeRepository extends JpaRepository<DiscussionLike, Long> {
    Optional<DiscussionLike> findByUserUserIdAndDiscussionId(Long userId, Long discussionId);
    Long countByDiscussionId(Long discussionId);
}