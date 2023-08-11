package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.LectureWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureWatchRepository extends JpaRepository<LectureWatch, Integer> {
    Optional<LectureWatch> findByLectureIdAndMemberId(int lectureId, Long memberId);
}
