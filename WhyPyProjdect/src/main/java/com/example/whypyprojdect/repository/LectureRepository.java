package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {
    // 필요한 추가 메서드를 선언할 수 있습니다.
    List<Lecture> findByTitleContaining(String keyword);
}
