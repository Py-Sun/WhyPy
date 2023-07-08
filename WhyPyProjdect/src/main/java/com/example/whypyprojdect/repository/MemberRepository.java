package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
}
