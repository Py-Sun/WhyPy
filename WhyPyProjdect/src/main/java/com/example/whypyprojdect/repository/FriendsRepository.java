package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
}