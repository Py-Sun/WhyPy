package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.MemberEntity;
import lombok.*;

@Getter
@Setter //스프링이 save.html에 있는 memberEmail, memberPassword, memberName과 자동 비교하여 알아서 값을 담아줌
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //필드를 모두 매개변수 라는 생성자 만들어 줌
@ToString //dto객체가 가진 값 출력할 때 ToString값 자동 생성
public class MemberDto {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String nickName;
    private String memberProfile;
    //save에 있는 name 속성들과 dto 필드들(위의 코드)이 동일하다면 스프링이 알아서 dto 객체를 만들어서
    //그 객체의 setter메소드를 호출해서 save에 작성한 값을 알아서 담아줌

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberEmail(memberEntity.getMemberEmail());
        memberDto.setMemberPassword(memberEntity.getMemberPassword());
        memberDto.setMemberName(memberEntity.getMemberName());
        memberDto.setNickName(memberEntity.getNickName());
        return memberDto;
    }

    }
