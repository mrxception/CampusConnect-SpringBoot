package com.cituconnect.service;

import com.cituconnect.entity.ForumLike;
import com.cituconnect.entity.DiscussionLike;
import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.User;
import com.cituconnect.repository.ForumLikeRepository; // Changed
import com.cituconnect.repository.DiscussionLikeRepository; // Changed
import com.cituconnect.repository.ForumRepository;
import com.cituconnect.repository.DiscussionRepository;
import com.cituconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    private ForumLikeRepository forumLikeRepository; // Changed

    @Autowired
    private DiscussionLikeRepository discussionLikeRepository; // Changed

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    public ForumLike toggleForumLike(Long userId, Long forumId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Forum> forum = forumRepository.findById(forumId);

        if (user.isEmpty() || forum.isEmpty()) {
            return null;
        }

        Optional<ForumLike> existingLike = forumLikeRepository.findByUserUserIdAndForumId(userId, forumId);

        if (existingLike.isPresent()) {
            // Unlike
            forumLikeRepository.delete(existingLike.get());
            return null;
        } else {
            // Like
            ForumLike like = ForumLike.builder() // Changed
                    .user(user.get())
                    .forum(forum.get())
                    .build();
            return forumLikeRepository.save(like); // Changed
        }
    }

    public DiscussionLike toggleDiscussionLike(Long userId, Long discussionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);

        if (user.isEmpty() || discussion.isEmpty()) {
            return null;
        }

        Optional<DiscussionLike> existingLike = discussionLikeRepository.findByUserUserIdAndDiscussionId(userId, discussionId); // Changed

        if (existingLike.isPresent()) {
            // Unlike
            discussionLikeRepository.delete(existingLike.get()); // Changed
            return null;
        } else {
            // Like
            DiscussionLike like = DiscussionLike.builder() // Changed
                    .user(user.get())
                    .discussion(discussion.get())
                    .build();
            return discussionLikeRepository.save(like); // Changed
        }
    }

    public boolean isForumLikedByUser(Long userId, Long forumId) {
        return forumLikeRepository.findByUserUserIdAndForumId(userId, forumId).isPresent(); // Changed
    }

    public boolean isDiscussionLikedByUser(Long userId, Long discussionId) {
        return discussionLikeRepository.findByUserUserIdAndDiscussionId(userId, discussionId).isPresent(); // Changed
    }

    public Long getForumLikeCount(Long forumId) {
        return forumLikeRepository.countByForumId(forumId); // Changed
    }

    public Long getDiscussionLikeCount(Long discussionId) {
        return discussionLikeRepository.countByDiscussionId(discussionId); // Changed
    }
}