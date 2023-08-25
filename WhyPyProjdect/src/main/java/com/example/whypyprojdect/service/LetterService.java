package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.Letter;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.LetterRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LetterService {
    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public LetterService(LetterRepository letterRepository, MemberRepository memberRepository)
    {
        this.letterRepository = letterRepository;
        this.memberRepository = memberRepository;
    }

    public Letter sendLetter(long senderId, long receiverId, String title, String content) {
        Letter letter = new Letter();
        letter.setSenderId(senderId);
        letter.setReceiverId(receiverId);
        letter.setTitle(title);
        letter.setContent(content);
        letter.setSendDate(new Date());
        return letterRepository.save(letter);
    }

    public List<Letter> getReceivedLetters(long receiverId) {
        return letterRepository.findByReceiverId(receiverId);
    }

    public List<Letter> getSentLetters(long senderId) {
        return letterRepository.findBySenderId(senderId);
    }

    public void deleteReceivedLetter(int letterId) {
        letterRepository.deleteById(letterId);
    }


    public void deleteSentLetter(int letterId) {
        letterRepository.deleteById(letterId);
    }

    public long getMemberID(MemberEntity member){
        return member.getId();
    }

}
