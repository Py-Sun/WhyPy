package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

// DB 로직 작성
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);
}
