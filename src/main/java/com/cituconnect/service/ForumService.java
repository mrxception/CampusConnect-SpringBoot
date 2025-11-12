package com.cituconnect.service;

import com.cituconnect.entity.Forum;
import com.cituconnect.repository.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;

    public Forum createForum(Forum forum) {
        forum.setCreatedAt(LocalDateTime.now());
        forum.setUpdatedAt(LocalDateTime.now());
        forum.setReplies(0);
        forum.setLikes(0);
        forum.setViews(0);
        return forumRepository.save(forum);
    }

    public Optional<Forum> getForumById(Long id) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum f = forum.get();
            f.setViews(f.getViews() + 1);
            forumRepository.save(f);
        }
        return forum;
    }

    public List<Forum> getAllForums() {
        return forumRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Forum> getForumsByCategory(String category) {
        return forumRepository.findByCategory(category);
    }

    public List<Forum> getForumsByUserId(Long userId) {
        return forumRepository.findByUserUserId(userId);
    }

    public List<Forum> searchForums(String title) {
        return forumRepository.findByTitleContainingIgnoreCase(title);
    }

    public Forum updateForum(Long id, Forum forumDetails) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum existingForum = forum.get();
            if (forumDetails.getTitle() != null) existingForum.setTitle(forumDetails.getTitle());
            if (forumDetails.getContent() != null) existingForum.setContent(forumDetails.getContent());
            if (forumDetails.getCategory() != null) existingForum.setCategory(forumDetails.getCategory());
            existingForum.setUpdatedAt(LocalDateTime.now());
            return forumRepository.save(existingForum);
        }
        return null;
    }

    public Forum addReply(Long id) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum f = forum.get();
            f.setReplies(f.getReplies() + 1);
            return forumRepository.save(f);
        }
        return null;
    }

    public Forum addLike(Long id) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            Forum f = forum.get();
            f.setLikes(f.getLikes() + 1);
            return forumRepository.save(f);
        }
        return null;
    }

    public void deleteForum(Long id) {
        forumRepository.deleteById(id);
    }

    public Forum findById(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

    public Forum save(Forum discussion) {
        return forumRepository.save(discussion);
    }

}
