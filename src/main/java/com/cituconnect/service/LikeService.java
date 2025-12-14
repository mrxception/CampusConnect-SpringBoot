package com.cituconnect.service;

import com.cituconnect.entity.ForumLike;
import com.cituconnect.entity.DiscussionLike;
import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.User;
import com.cituconnect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired private ForumLikeRepository forumLikeRepository;
    @Autowired private DiscussionLikeRepository discussionLikeRepository;
    @Autowired private ForumRepository forumRepository;
    @Autowired private DiscussionRepository discussionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private NotificationService notificationService;

    public ForumLike toggleForumLike(Long userId, Long forumId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Forum> forum = forumRepository.findById(forumId);

        if (user.isEmpty() || forum.isEmpty()) return null;

        Optional<ForumLike> existingLike = forumLikeRepository.findByUserUserIdAndForumId(userId, forumId);

        if (existingLike.isPresent()) {
            forumLikeRepository.delete(existingLike.get());
            return null;
        } else {
            ForumLike like = ForumLike.builder()
                    .user(user.get())
                    .forum(forum.get())
                    .build();
            ForumLike saved = forumLikeRepository.save(like);

            User owner = forum.get().getUser();
            if (!owner.getUserId().equals(userId)) {
                notificationService.createNotification(
                        owner.getUserId(),
                        "New Like",
                        user.get().getName() + " liked your post: \"" + forum.get().getTitle() + "\"",
                        "LIKE",
                        forumId
                );
            }
            return saved;
        }
    }

    public DiscussionLike toggleDiscussionLike(Long userId, Long discussionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);

        if (user.isEmpty() || discussion.isEmpty()) return null;

        Optional<DiscussionLike> existingLike = discussionLikeRepository.findByUserUserIdAndDiscussionId(userId, discussionId);

        if (existingLike.isPresent()) {
            discussionLikeRepository.delete(existingLike.get());
            return null;
        } else {
            DiscussionLike like = DiscussionLike.builder()
                    .user(user.get())
                    .discussion(discussion.get())
                    .build();
            DiscussionLike saved = discussionLikeRepository.save(like);

            User owner = discussion.get().getUser();
            if (!owner.getUserId().equals(userId)) {
                Long forumId = discussion.get().getForum() != null ? discussion.get().getForum().getId() : null;

                notificationService.createNotification(
                        owner.getUserId(),
                        "New Like",
                        user.get().getName() + " liked your comment.",
                        "LIKE",
                        forumId
                );
            }
            return saved;
        }
    }

    public boolean isForumLikedByUser(Long userId, Long forumId) {
        return forumLikeRepository.findByUserUserIdAndForumId(userId, forumId).isPresent();
    }

    public boolean isDiscussionLikedByUser(Long userId, Long discussionId) {
        return discussionLikeRepository.findByUserUserIdAndDiscussionId(userId, discussionId).isPresent();
    }

    public Long getForumLikeCount(Long forumId) {
        return forumLikeRepository.countByForumId(forumId);
    }

    public Long getDiscussionLikeCount(Long discussionId) {
        return discussionLikeRepository.countByDiscussionId(discussionId);
    }
}