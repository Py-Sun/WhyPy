package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Follow;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface FollowRepository extends JpaRepository<Follow, Integer> {

    int countByFollowerIdAndFollowingMemberName(Long id, String memberName); // 팔로우 되어있는지 count하는 메서드

    @Modifying
    @Transactional
    void deleteByFollowingIdAndFollowerId(Long following_id, Long follower_id); // 언팔로우 메서드

}