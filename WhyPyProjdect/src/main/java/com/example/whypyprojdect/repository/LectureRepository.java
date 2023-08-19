package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Lecture> findByTitleContaining(String keyword);
    
    // 카테고리로 조회
    List<Lecture> findByCategory(int categoryId);
}
