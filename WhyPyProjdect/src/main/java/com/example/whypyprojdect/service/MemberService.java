package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //스프링이 관리하는 스프링 빈으로 등록
@RequiredArgsConstructor
public class MemberService {
    //생성자 주입
    private final MemberRepository memberRepository;

    public void save(MemberDto memberDto) {
        //1. dto > entity 변환
        //2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
        //memberEntity의 toMemberEntity메소드를 매개변수 memberDto 이용해서 호출
        //그리고 변환된 entity를 가져와야 하므로 Member Entity memberEntity= ~~
        memberRepository.save(memberEntity);
        //(jpa 제공하는) 래파지토리 save 메소드 호출
        //memberRepository의 save 메소드 호출 (조건: entity 객체를 넘겨줘야 함)
    }

    public MemberDto login(MemberDto memberDto) {
        /*
          1.회원이 입력한 이메일로 db에서 조회를 함
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
            //조회 결과가 없다(해당 아이디를 가진 회원이 없다)
            return null;
        }
    }

    public MemberDto updateForm(String myName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberName(myName);
        if (optionalMemberEntity.isPresent()) {
            return MemberDto.toMemberDto((optionalMemberEntity.get()));
        } else {
            return null;
        }
    }

    public void update(MemberDto memberDto) {
        memberRepository.save(MemberEntity.toUpateMemberEntity(memberDto));
    }

    public MemberEntity findByMemberName(String memberName) {
          return memberRepository.findByMemberName(memberName).orElseThrow(()-> {
        return new IllegalArgumentException("예외");
          });
    }

    public MemberEntity findById(Long id) {
       return memberRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("예외");
       });
    }
}