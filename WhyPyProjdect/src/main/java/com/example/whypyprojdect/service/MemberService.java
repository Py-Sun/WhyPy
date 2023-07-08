package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDto memberDto) {
        //1. dto > entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);
        //repository의 save 메소드 호출 (조건. entity 객체를 넘겨줘야 함)
    }
}
