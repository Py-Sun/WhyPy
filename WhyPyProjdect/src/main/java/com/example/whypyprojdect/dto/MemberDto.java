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

    public static MemberDto toMemberDTO(MemberEntity memberEntity) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(memberEntity.getId());
        memberDto.setMemberEmail(memberEntity.getMemberEmail());
        memberDto.setMemberPassword(memberEntity.getMemberPassword());
        memberDto.setMemberName(memberEntity.getMemberName());
        return memberDto;
    }
}
