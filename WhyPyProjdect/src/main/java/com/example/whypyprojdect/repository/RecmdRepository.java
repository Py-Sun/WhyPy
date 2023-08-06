package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Recmd;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RecmdRepository extends JpaRepository<Recmd, Integer> {
    Optional<Recmd> findByPostIdAndMemberId(Integer postId, Long memberId);

    int countByPostIdAndRecmdDateAfter(Integer postId, String date);
}