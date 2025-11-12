package com.cituconnect.repository;

import com.cituconnect.entity.LostFound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LostFoundRepository extends JpaRepository<LostFound, Long> {
    List<LostFound> findByStatus(String status);
    List<LostFound> findByCategory(String category);
    List<LostFound> findByUserUserId(Long userId);
    List<LostFound> findByTitleContainingIgnoreCase(String title);
    List<LostFound> findByStatusAndCategory(String status, String category);
}
