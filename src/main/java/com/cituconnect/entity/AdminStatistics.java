package com.cituconnect.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminStatistics {
    private Long totalUsers;
    private Long activeUsers;
    private Long totalForums;
    private Long totalDiscussions;
    private Long totalLostFoundPosts;
    private Long totalMessages;
    private Long totalNotifications;
}
