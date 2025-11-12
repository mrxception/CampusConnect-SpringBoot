package com.cituconnect.service;

import com.cituconnect.entity.Discussion;
import com.cituconnect.repository.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {
    @Autowired
    private DiscussionRepository discussionRepository;

    public Discussion createDiscussion(Discussion discussion) {
        discussion.setCreatedAt(LocalDateTime.now());
        discussion.setUpdatedAt(LocalDateTime.now());
        discussion.setLikes(0);
        return discussionRepository.save(discussion);
    }

    public Optional<Discussion> getDiscussionById(Long id) {
        return discussionRepository.findById(id);
    }

    public List<Discussion> getDiscussionsByForumId(Long forumId) {
        return discussionRepository.findByForumIdOrderByCreatedAtDesc(forumId);
    }

    public List<Discussion> getDiscussionsByUserId(Long userId) {
        return discussionRepository.findByUserUserId(userId);
    }

    public Discussion updateDiscussion(Long id, Discussion discussionDetails) {
        Optional<Discussion> discussion = discussionRepository.findById(id);
        if (discussion.isPresent()) {
            Discussion existingDiscussion = discussion.get();
            if (discussionDetails.getContent() != null) existingDiscussion.setContent(discussionDetails.getContent());
            existingDiscussion.setUpdatedAt(LocalDateTime.now());
            return discussionRepository.save(existingDiscussion);
        }
        return null;
    }

    public Discussion addLike(Long id) {
        Optional<Discussion> discussion = discussionRepository.findById(id);
        if (discussion.isPresent()) {
            Discussion d = discussion.get();
            d.setLikes(d.getLikes() + 1);
            return discussionRepository.save(d);
        }
        return null;
    }

    public void deleteDiscussion(Long id) {
        discussionRepository.deleteById(id);
    }

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public Discussion findById(Long id) {
        return discussionRepository.findById(id).orElse(null);
    }

    public Discussion save(Discussion discussion) {
        return discussionRepository.save(discussion);
    }

}
