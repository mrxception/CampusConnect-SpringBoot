package com.cituconnect.service;

import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.Forum;
import com.cituconnect.repository.DiscussionRepository;
import com.cituconnect.repository.ForumRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    @Autowired
    private ForumRepository forumRepository;

    public Discussion createDiscussion(Discussion discussion) {

        discussion.setCreatedAt(LocalDateTime.now());
        discussion.setUpdatedAt(LocalDateTime.now());
        discussion.setLikes(0);

        Discussion saved = discussionRepository.save(discussion);

        Long count = discussionRepository.countByForumId(discussion.getForum().getId());

        Forum forum = forumRepository.findById(discussion.getForum().getId()).orElse(null);
        if (forum != null) {
            forum.setReplies(count.intValue());
            forumRepository.save(forum);
        }

        return saved;
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

    public Discussion updateDiscussion(Long id, Discussion details) {
        Optional<Discussion> optional = discussionRepository.findById(id);

        if (optional.isPresent()) {
            Discussion d = optional.get();

            if (details.getContent() != null)
                d.setContent(details.getContent());

            d.setUpdatedAt(LocalDateTime.now());
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
