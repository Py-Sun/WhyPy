package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Friends> findByReceiverIdAndState(Long receiverId, String state);
    Friends findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    @Query("SELECT f FROM Friends f WHERE (f.senderId = :memberId OR f.receiverId = :memberId) AND f.state = 'received'")
    List<Friends> findFriends(Long memberId);
}