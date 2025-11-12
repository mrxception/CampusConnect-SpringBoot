package com.cituconnect.controller;

import com.cituconnect.entity.LostFound;
import com.cituconnect.service.LostFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
@RequestMapping("/lost-found")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class LostFoundController {
    @Autowired
    private LostFoundService lostFoundService;

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody LostFound item) {
        LostFound createdItem = lostFoundService.createItem(item);
        return ResponseEntity.ok(Map.of("message", "Item created successfully", "item", createdItem));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItemById(@PathVariable Long id) {
        Optional<LostFound> item = lostFoundService.getItemById(id);
        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item.get());
    }

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        List<LostFound> items = lostFoundService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getItemsByStatus(@PathVariable String status) {
        List<LostFound> items = lostFoundService.getItemsByStatus(status);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String category) {
        List<LostFound> items = lostFoundService.getItemsByCategory(category);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getItemsByUserId(@PathVariable Long userId) {
        List<LostFound> items = lostFoundService.getItemsByUserId(userId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchItems(@PathVariable String title) {
        List<LostFound> items = lostFoundService.searchItems(title);
        return ResponseEntity.ok(items);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody LostFound itemDetails) {
        LostFound updatedItem = lostFoundService.updateItem(id, itemDetails);
        if (updatedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        lostFoundService.deleteItem(id);
        return ResponseEntity.ok(Map.of("message", "Item deleted successfully"));
    }

    @GetMapping("/resolved/list")
    public ResponseEntity<?> getResolvedItems() {
        List<LostFound> items = lostFoundService.getResolvedItems();
        return ResponseEntity.ok(items);
    }
}
