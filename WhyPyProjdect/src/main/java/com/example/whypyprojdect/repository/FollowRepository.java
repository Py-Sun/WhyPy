package com.example.whypyprojdect.repository;

import java.util.List;
import java.util.Optional;

import com.example.whypyprojdect.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, String> {

    List<Follow> findByFollowerId(String fromId);

    Long deleteByFollowerIdAndFollowingId(String fromId, String toId);

}