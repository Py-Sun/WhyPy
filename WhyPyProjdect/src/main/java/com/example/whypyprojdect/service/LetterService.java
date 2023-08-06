package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.LetterDto;
import com.example.whypyprojdect.entity.Letter;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.LetterRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LetterService {
    private final LetterRepository letterRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public LetterDto write(LetterDto letterDto) {
        //메세지 작성할 떄 닉네임 사용
        MemberEntity letterReceiver = memberRepository.findByNickName(letterDto.getLetterReceiverName());
        MemberEntity letterSender = memberRepository.findByNickName(letterDto.getLetterSenderName());

        Letter letter = new Letter();
        letter.setLetterReceiver(letterReceiver);
        letter.setLetterSender(letterSender);

        letter.setLetterTitle(letterDto.getLetterTitle());
        letter.setLetterContent(letterDto.getLetterContent());
        letter.setDeletedByLetterReceiver(false);
        letter.setDeletedByLetterSender(false);
        letterRepository.save(letter);

        return LetterDto.toDto(letter);
    }

    @Transactional//(readOnly=true)
    public List<LetterDto> receivedLetter(MemberEntity memberEntity) {
        //받은 편지함 불러오기
        //한 명의 유저가 받은 모든 메시지
        List<Letter> letters = letterRepository.findAllByLetterReceiver(memberEntity);
        List<LetterDto> letterDtos=new ArrayList<>();

        for(Letter letter:letters) {
            //leeter에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내준다
            if(!letter.isDeletedByLetterReceiver()) {
                letterDtos.add(LetterDto.toDto(letter));
            }
        }
        return letterDtos;
    }

    //받은 편지 삭제
    @Transactional
    public  Object deletedLetterByLetterReceiver(int letterId, MemberEntity memberEntity) {
        Letter letter= (Letter) letterRepository.findByLetterId(letterId).orElseThrow(() -> {
            return new IllegalArgumentException("메세지를 찾을 수 없습니다.");
        });

        if(memberEntity==letter.getLetterSender()) {
            letter.deleteByLetterReceiver();; //받은 사람에게 메세지 삭제
            if (letter.isDeleted()) {
                //받은 사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제 요청
                letterRepository.delete(letter);
                return "양쪽 모두 삭제";
            }
            return "한 쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }

    @Transactional//(readOnly=true)
    public List<LetterDto> sentLetter(MemberEntity memberEntity) {
        //보낸 편지함 불러오기
        //한 명의 유저가 받은 모든 letter
        List<Letter> letters = letterRepository.findAllByLetterSender(memberEntity);
        List<LetterDto> letterDtos = new ArrayList<>();

        for (Letter letter : letters) {
            //letter에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보낸다
            if (!letter.isDeletedByLetterSender()) {
                letterDtos.add(LetterDto.toDto(letter));
            }
        }
        return letterDtos;
    }

    //보낸 편지 삭제
    @Transactional
    public Object deleteLetterByLetterSender(int letterId, MemberEntity memberEntity) {
        Letter letter= (Letter) letterRepository.findByLetterId(letterId).orElseThrow(() -> {
            return new IllegalArgumentException("메세지를 찾을 수 없습니다.");
        });

        if(memberEntity==letter.getLetterSender()) {
            letter.deleteByLetterSender(); //받은 사람에게 letter 삭제
            if (letter.isDeleted()) {
                //받은 사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제 요청
                letterRepository.delete(letter);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }


    }
}
