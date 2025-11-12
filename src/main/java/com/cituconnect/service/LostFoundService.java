package com.cituconnect.service;

import com.cituconnect.entity.LostFound;
import com.cituconnect.repository.LostFoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LostFoundService {
    @Autowired
    private LostFoundRepository lostFoundRepository;

    public LostFound createItem(LostFound item) {
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setViews(0);
        return lostFoundRepository.save(item);
    }

    public Optional<LostFound> getItemById(Long id) {
        Optional<LostFound> item = lostFoundRepository.findById(id);
        if (item.isPresent()) {
            LostFound lf = item.get();
            lf.setViews(lf.getViews() + 1);
            lostFoundRepository.save(lf);
        }
        return item;
    }

    public List<LostFound> getAllItems() {
        return lostFoundRepository.findAll();
    }

    public List<LostFound> getItemsByStatus(String status) {
        return lostFoundRepository.findByStatus(status);
    }

    public List<LostFound> getItemsByCategory(String category) {
        return lostFoundRepository.findByCategory(category);
    }

    public List<LostFound> getItemsByUserId(Long userId) {
        return lostFoundRepository.findByUserUserId(userId);
    }

    public List<LostFound> searchItems(String title) {
        return lostFoundRepository.findByTitleContainingIgnoreCase(title);
    }

    public LostFound updateItem(Long id, LostFound itemDetails) {
        Optional<LostFound> item = lostFoundRepository.findById(id);
        if (item.isPresent()) {
            LostFound existingItem = item.get();
            if (itemDetails.getTitle() != null) existingItem.setTitle(itemDetails.getTitle());
            if (itemDetails.getDescription() != null) existingItem.setDescription(itemDetails.getDescription());
            if (itemDetails.getStatus() != null) existingItem.setStatus(itemDetails.getStatus());
            if (itemDetails.getLocation() != null) existingItem.setLocation(itemDetails.getLocation());
            if (itemDetails.getCategory() != null) existingItem.setCategory(itemDetails.getCategory());
            existingItem.setUpdatedAt(LocalDateTime.now());
            return lostFoundRepository.save(existingItem);
        }
        return null;
    }

    public void deleteItem(Long id) {
        lostFoundRepository.deleteById(id);
    }

    public List<LostFound> getResolvedItems() {
        return lostFoundRepository.findByStatus("FOUND");
    }
}
