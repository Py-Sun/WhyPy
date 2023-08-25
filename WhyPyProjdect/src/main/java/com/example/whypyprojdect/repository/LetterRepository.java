package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Letter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetterRepository extends JpaRepository<Letter, Integer> {
    List<Letter> findByReceiverId(long receiverId);
    List<Letter> findBySenderId(long senderId);
}
