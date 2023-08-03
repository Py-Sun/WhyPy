package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.QuestionSolve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionSolveRepository extends JpaRepository<QuestionSolve, Integer> {
    Optional<QuestionSolve> findByQuestionIdAndMemberId(Integer questionId, Long memberId);
    List<QuestionSolve> findAllByMemberId(Long memberId);
}
