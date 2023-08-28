package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Post> findByTitleContainingIgnoreCase(String keyword);

    List<Post> findByBoard(String board);
}
