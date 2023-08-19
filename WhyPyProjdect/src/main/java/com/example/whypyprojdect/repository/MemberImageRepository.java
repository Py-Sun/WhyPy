package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberImageRepository extends JpaRepository<MemberEntity, Integer> {

    Optional<MemberEntity> findByMemberName(String memberName);

    //MemberEntity findBymemberNameAndmemeberPassword(String memberName, String memberPassword);

}
