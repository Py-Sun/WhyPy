package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Letter;
import com.example.whypyprojdect.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LetterRepository extends JpaRepository<Letter, Integer> {
    List<Letter> findAllByLetterReceiver(MemberEntity memberEntity);
    List<Letter> findAllByLetterSender(MemberEntity memberEntity);
    Optional<Object> findByLetterId(int letterId);
}
