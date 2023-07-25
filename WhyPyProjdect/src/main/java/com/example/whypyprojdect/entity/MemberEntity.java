package com.example.whypyprojdect.entity;

//해당 파일은 데이터베이스의 테이블을 자바 객체처럼 활용할 수 있게 해줌

import com.example.whypyprojdect.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity //엔티티 정의
@Setter
@Getter
@Table(name="member_table") //실제 생성됐을 때 테이블 이름 정의
public class MemberEntity {

    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 지정
    @Column(name="id")
    private Long id;

    @Column
    private String memberEmail; //db로 들어갈때는 대문자 앞에 언더바 들어가며 변경됨. >memeber_email

    @Column
    private String memberPassword;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberName;

    @Column
    private String memberProfile;

    //엔티티 객체를 객체로 만들어서 호출하는 게 아닌 그냥 클래스 메소드로 정의
    public static MemberEntity toMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setMemberProfile(memberDto.getMemberProfile());
        //dto에 담긴 것을 entity로 넘김(변환)
        //에러가 나거나 값이 생각한 값이 아니면 이 부분에서 문제가 있을 가능성 큼
        return memberEntity;
    }

    public static MemberEntity toUpateMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDto.getId());
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setMemberProfile(memberDto.getMemberProfile());
        return memberEntity;
    }
}
