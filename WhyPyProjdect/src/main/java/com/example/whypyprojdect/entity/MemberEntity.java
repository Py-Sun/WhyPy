package com.example.whypyprojdect.entity;

//해당 파일은 데이터베이스의 테이블을 자바 객체처럼 활용할 수 있게 해줌

import com.example.whypyprojdect.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.springframework.web.multipart.MultipartFile;


@Entity //엔티티 정의
@Setter
@Getter
@Table(name="member_table") //실제 생성됐을 때 테이블 이름 정의
public class MemberEntity implements Serializable {

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
    private String nickName;

    @Column
    private String memberImage;

    @Column
    private String ImagePath;

    @Column
    private String createdAt;

    public void setcreatedAt(String createdAt) {this.createdAt = createdAt;}
    @PrePersist
    public void createDate(){
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    //엔티티 객체를 객체로 만들어서 호출하는 게 아닌 그냥 클래스 메소드로 정의
    public static MemberEntity toMemberEntity(MemberDto memberDto) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDto.getMemberEmail());
        memberEntity.setMemberPassword(memberDto.getMemberPassword());
        memberEntity.setMemberName(memberDto.getMemberName());
        memberEntity.setNickName(memberDto.getNickName());
        memberEntity.setMemberImage(String.valueOf(memberDto.getMemberImage()));
        memberEntity.setImagePath(memberDto.getImagePath());
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
        memberEntity.setNickName(memberDto.getNickName());
        memberEntity.setMemberImage(String.valueOf(memberDto.getMemberImage()));
        memberEntity.setImagePath(memberDto.getImagePath());
        return memberEntity;
    }
//
//    @Builder
//    public Member(Long id, String memberName, String password) {
//        this.id = id;
//        this.memberName = memberName;
//        this.memberPassword = memberPassword();
//        this.createdAt = new Date();
//    }
}