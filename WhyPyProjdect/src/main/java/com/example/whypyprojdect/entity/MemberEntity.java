package com.example.whypyprojdect.entity;

//해당 파일은 데이터베이스의 테이블을 자바 객체처럼 활용할 수 있게 해줌

import com.example.whypyprojdect.dto.MemberDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Setter
@Getter
@Table(name="member_table") //실제 생성됐을 때 테이블 이름 정함
public class MemberEntity {

    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment 지정
    @Column(name="id")
    private Long id;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        //dto에 담긴 것을 entity로 넘김(변환)
        return memberEntity;
    }
}
