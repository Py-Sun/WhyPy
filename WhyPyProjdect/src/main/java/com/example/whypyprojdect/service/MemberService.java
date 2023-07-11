package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDto memberDto) {
        //1. dto > entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        memberRepository.save(memberEntity);
        //repository의 save 메소드 호출 (조건: entity 객체를 넘겨줘야 함)
    }

    public MemberDto login(MemberDto memberDto) {
        /*
          1.회원이 입력한 아이디로 db에서 조회를 함
          2.db에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberName = memberRepository.findByMemberName(memberDto.getMemberName());
        if (byMemberName.isPresent()) {
            // 조회 결과가 있다 (해당 이메일을 가진 회원 정보가 있다)
            MemberEntity memberEntity=byMemberName.get();
            if (memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
                //login 성공
                //entity ->dto 변환 후 리턴
                MemberDto dto=MemberDto.toMemberDto(memberEntity);
                return dto;
            } else {
                //login 실패
                return null;
            }
        } else {
            //조회 결과가 없다(해당 이메일을 가진 회원이 없다)
            return null;
        }
    }
}
