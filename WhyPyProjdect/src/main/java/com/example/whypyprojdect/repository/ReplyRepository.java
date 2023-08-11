package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Reply> findByPostId(int postId);
}