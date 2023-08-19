package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.KeywordRank;
import com.example.whypyprojdect.repository.KeywordRankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class KeywordRankService {

    @Autowired
    private KeywordRankRepository keywordRankRepository;

    // 검색 키워드 순위 업데이트
    public void updateKeywordRank(String keyword) {
        Date currentDate  = findCurrendDate();

        // 키워드와 날짜 조회
        KeywordRank keywordRank = keywordRankRepository.findByKeywordAndSearchDate(keyword, currentDate);

        if (keywordRank == null) {
            System.out.println("insert KeyWord");
            keywordRank = new KeywordRank();
            keywordRank.setKeyword(keyword);
            keywordRank.setSearchCount(1L);
            keywordRank.setSearchDate(currentDate);
        } else {
            System.out.println("update KeyWord");
            keywordRank.setSearchCount(keywordRank.getSearchCount() + 1);
        }

        keywordRankRepository.save(keywordRank);
    }

    // 검색 순위 중 상위 10개 반환
    public List<KeywordRank> getTop10TodayKeywordRank() {
        Date currentDate  = findCurrendDate();

        // 상위 10개 검색 결과를 반환
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "searchCount"));
        return keywordRankRepository.findTop10BySearchDate(currentDate, pageable);
    }

    private Date findCurrendDate()
    {
        // 현재 날짜 구하기 (00:00:00으로 변경 후 저장 & 조회)
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); // 밀리초 정보도 제거

        Date currentDate = calendar.getTime();

        return currentDate;
    }
}
