package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.LectureWatch;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.LectureWatchRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LectureWatchService {

    private final LectureWatchRepository lectureWatchRepository;
    private final MemberRepository memberRepository;

    public void saveOrupdateViewDate(LectureWatch lectureWatch) {
        Optional<LectureWatch> existingWatch = lectureWatchRepository.findByLectureIdAndMemberId(lectureWatch.getLectureId(), lectureWatch.getMemberId());

        if (existingWatch.isPresent()) {
            // 이미 레코드가 존재하면 업데이트
            System.out.println("Save Date1");
            LectureWatch LectureWatchEntity = existingWatch.get();
            LectureWatchEntity.setLDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            lectureWatchRepository.save(LectureWatchEntity);
        } else {
            // 없으면 새 레코드 추가
            System.out.println("Save Date2");
            lectureWatch.createDate();
            lectureWatchRepository.save(lectureWatch);
        }
    }

    public void setLectureId(LectureWatch lectureWatch, int lectureId)
    {
        lectureWatch.setLectureId(lectureId);
    }

    public void setMemberId(LectureWatch lectureWatch, Object memberId)
    {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) memberId);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            lectureWatch.setMemberId(member.getId());
        }
    }
}
