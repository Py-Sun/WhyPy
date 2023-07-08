package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//cf. 레파지토리로 작업할 때는 반드시 엔티티로 넘겨줘야 함
public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
}
