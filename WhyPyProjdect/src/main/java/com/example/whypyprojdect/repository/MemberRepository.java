package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//cf. 레파지토리로 작업할 때는 반드시 엔티티로 넘겨줘야 함
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
    //아이디로 회원 정보 조회
   Optional <MemberEntity> findByMemberName(String memberName);

    //MemberEntity findBymemberNameAndmemeberPassword(String memberName, String memberPassword);

//optional = null 방지


}
