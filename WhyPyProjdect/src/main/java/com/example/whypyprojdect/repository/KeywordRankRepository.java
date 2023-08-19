package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.KeywordRank;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KeywordRankRepository extends JpaRepository<KeywordRank, Long> {

    // 키워드와 날짜로 엔티티 검색
    KeywordRank findByKeywordAndSearchDate(String keyword, Date searchDate);

    // 오늘 검색어 순위 중 상위 10개 반환
    List<KeywordRank> findTop10BySearchDate(Date searchDate, Pageable pageable);
}