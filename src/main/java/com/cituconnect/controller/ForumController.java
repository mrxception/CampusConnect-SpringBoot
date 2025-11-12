package com.cituconnect.controller;

import com.cituconnect.entity.Forum;
import com.cituconnect.entity.Like;
import com.cituconnect.entity.User;
import com.cituconnect.repository.ForumRepository;
import com.cituconnect.service.ForumService;
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
@RequestMapping("/forum")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class ForumController {
    @Autowired
    private ForumService forumService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> createForum(@RequestBody Forum forum) {
        Forum createdForum = forumService.createForum(forum);
        return ResponseEntity.ok(Map.of("message", "Forum created successfully", "forum", createdForum));
    }


    @GetMapping
    public ResponseEntity<?> getAllForums() {
        List<Forum> forums = forumService.getAllForums();

        for (Forum forum : forums) {
            Long likeCount = likeService.getForumLikeCount(forum.getId());
            forum.setLikes(likeCount.intValue());
        }

        return ResponseEntity.ok(forums);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getForumsByCategory(@PathVariable String category) {
        List<Forum> forums = forumService.getForumsByCategory(category);
        return ResponseEntity.ok(forums);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getForumsByUserId(@PathVariable Long userId) {
        List<Forum> forums = forumService.getForumsByUserId(userId);
        return ResponseEntity.ok(forums);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchForums(@PathVariable String title) {
        List<Forum> forums = forumService.searchForums(title);
        return ResponseEntity.ok(forums);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateForum(@PathVariable Long id, @RequestBody Forum forumDetails) {
        Forum updatedForum = forumService.updateForum(id, forumDetails);
        if (updatedForum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedForum);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<?> addReply(@PathVariable Long id) {
        Forum forum = forumService.addReply(id);
        if (forum == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("message", "Reply added", "forum", forum));
    }

    @Autowired
    private LikeService likeService;

    @Autowired
    private ForumRepository forumRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getForumPost(@PathVariable Long id) {
        Optional<Forum> forum = forumRepository.findById(id);

        if (forum.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Forum post = forum.get();
        Long likeCount = likeService.getForumLikeCount(id);
        post.setLikes(likeCount.intValue());

        return ResponseEntity.ok(post);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> toggleLike(
            @PathVariable Long id,
            @RequestParam(required = false) Long userId) {

        if (userId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "userId is required"));
        }

        Like result = likeService.toggleForumLike(userId, id);
        boolean isLiked = result != null;
        Long likeCount = likeService.getForumLikeCount(id);

        return ResponseEntity.ok(Map.of(
                "message", isLiked ? "Post liked" : "Post unliked",
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
            isLiked = likeService.isForumLikedByUser(userId, id);
        }

        Long likeCount = likeService.getForumLikeCount(id);

        return ResponseEntity.ok(Map.of(
                "isLiked", isLiked,
                "likeCount", likeCount
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteForum(@PathVariable Long id) {
        forumService.deleteForum(id);
        return ResponseEntity.ok(Map.of("message", "Forum deleted successfully"));
    }
}
