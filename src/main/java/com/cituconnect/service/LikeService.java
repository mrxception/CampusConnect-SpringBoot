package com.cituconnect.service;

import com.cituconnect.entity.Like;
import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.User;
import com.cituconnect.repository.LikeRepository;
import com.cituconnect.repository.ForumRepository;
import com.cituconnect.repository.DiscussionRepository;
import com.cituconnect.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private ForumRepository forumRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private UserRepository userRepository;

    public Like toggleForumLike(Long userId, Long forumId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Forum> forum = forumRepository.findById(forumId);

        if (user.isEmpty() || forum.isEmpty()) return null;

        Optional<Like> existing = likeRepository.findByUserUserIdAndForumId(userId, forumId);

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());

            Long newCount = likeRepository.countByForumId(forumId);
            Forum f = forum.get();
            f.setLikes(newCount.intValue());
            forumRepository.save(f);

            return null;
        }

        Like like = Like.builder()
                .user(user.get())
                .forum(forum.get())
                .build();

        Like savedLike = likeRepository.save(like);

        Long newCount = likeRepository.countByForumId(forumId);
        Forum f = forum.get();
        f.setLikes(newCount.intValue());
        forumRepository.save(f);

        return savedLike;
    }

    public Like toggleDiscussionLike(Long userId, Long discussionId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Discussion> discussion = discussionRepository.findById(discussionId);

        if (user.isEmpty() || discussion.isEmpty()) return null;

        Optional<Like> existing = likeRepository.findByUserUserIdAndDiscussionId(userId, discussionId);

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return null;
        }

        Like like = Like.builder()
                .user(user.get())
                .discussion(discussion.get())
                .build();

        return likeRepository.save(like);
    }

    public Long getForumLikeCount(Long forumId) {
        return likeRepository.countByForumId(forumId);
    }

    public Long getDiscussionLikeCount(Long discussionId) {
        return likeRepository.countByDiscussionId(discussionId);
    }

    public boolean isForumLikedByUser(Long userId, Long forumId) {
        return likeRepository.findByUserUserIdAndForumId(userId, forumId).isPresent();
    }

    public boolean isDiscussionLikedByUser(Long userId, Long discussionId) {
        return likeRepository.findByUserUserIdAndDiscussionId(userId, discussionId).isPresent();
    }
}
