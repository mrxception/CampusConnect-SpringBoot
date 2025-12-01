package com.cituconnect.controller;

import com.cituconnect.entity.Discussion;
import com.cituconnect.entity.DiscussionLike; // Changed: Import DiscussionLike
import com.cituconnect.entity.User;
import com.cituconnect.repository.DiscussionRepository;
import com.cituconnect.service.DiscussionService;
import com.cituconnect.service.LikeService;
import com.cituconnect.service.UserService;
import com.cituconnect.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/discussion")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class DiscussionController {
    @Autowired
    private DiscussionService discussionService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private DiscussionRepository discussionRepository;

    @PostMapping
    public ResponseEntity<?> createDiscussion(@RequestBody Discussion discussion) {
        Discussion createdDiscussion = discussionService.createDiscussion(discussion);
        return ResponseEntity.ok(Map.of("message", "Discussion created successfully", "discussion", createdDiscussion));
    }


    @GetMapping
    public ResponseEntity<?> getAllDiscussions() {
        List<Discussion> discussions = discussionService.getAllDiscussions();

        for (Discussion discussion : discussions) {
            Long likeCount = likeService.getDiscussionLikeCount(discussion.getId());
            discussion.setLikes(likeCount.intValue());
        }

        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/forum/{forumId}")
    public ResponseEntity<?> getDiscussionsByForumId(@PathVariable Long forumId) {
        List<Discussion> discussions = discussionService.getDiscussionsByForumId(forumId);
        return ResponseEntity.ok(discussions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getDiscussionsByUserId(@PathVariable Long userId) {
        List<Discussion> discussions = discussionService.getDiscussionsByUserId(userId);
        return ResponseEntity.ok(discussions);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDiscussion(@PathVariable Long id, @RequestBody Discussion discussionDetails) {
        Discussion updatedDiscussion = discussionService.updateDiscussion(id, discussionDetails);
        if (updatedDiscussion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDiscussion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDiscussionReply(@PathVariable Long id) {
        Optional<Discussion> discussion = discussionRepository.findById(id);

        if (discussion.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Discussion reply = discussion.get();
        Long likeCount = likeService.getDiscussionLikeCount(id);
        reply.setLikes(likeCount.intValue());

        return ResponseEntity.ok(reply);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "userId is required"));
        }

        DiscussionLike result = likeService.toggleDiscussionLike(userId, id);
        boolean isLiked = result != null;
        Long likeCount = likeService.getDiscussionLikeCount(id);

        return ResponseEntity.ok(Map.of(
                "message", isLiked ? "Reply liked" : "Reply unliked",
                "isLiked", isLiked,
                "likeCount", likeCount
        ));
    }

    @GetMapping("/{id}/like-status")
    public ResponseEntity<?> getLikeStatus(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {

        boolean isLiked = false;
        if (userId != null) {
            isLiked = likeService.isDiscussionLikedByUser(userId, id);
        }

        Long likeCount = likeService.getDiscussionLikeCount(id);

        return ResponseEntity.ok(Map.of(
                "isLiked", isLiked,
                "likeCount", likeCount
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiscussion(@PathVariable Long id) {
        discussionService.deleteDiscussion(id);
        return ResponseEntity.ok(Map.of("message", "Discussion deleted successfully"));
    }
}