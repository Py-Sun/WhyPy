/*
package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.LetterDto;
import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.LetterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.xml.ws.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LetterController {

    private final LetterService letterService;
    private final MemberRepository memberRepository;

    //@ApiOperation(value="쪽지 보내기", notes ="쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/letters")
    public Response<?> sendLetter(@RequestBody LetterDto letterDto, HttpSession httpSession, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        MemberEntity memberEntity = memberRepository.findById(session.loginName);
        letterDto.setLetterSenderName(memberEntity.getNickName());

        return new Response<>("성공", "쪽지를 보냈습니다.", letterService.write(letterDto));

    }

    //@ApiOperation(value="받은 편지함 읽기", notes="받은 편지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/letters/received")
    public Response<?> getReceivedLetter() {
        MemberEntity memberEntity = memberRepository.findById(2).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response("성공", "받은 쪽지를 불러왔습니다.", letterService.receivedLetter(memberEntity));
    }

    //@ApiOperation(value="받은 쪽지 삭제하기", notes="받은 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/letters/received/{letterId}")
    public Response<?> deleteReceivedLetter(@PathVariable("letterId") Integer letterId) {
        MemberEntity memberEntity = memberRepository.findById(1).orElseThrow(()-> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("삭제 성공", "받은 쪽지인, "+ letterId +"번 쪽지를 삭제했습니다.", letterService.deletedLetterByLetterReceiver(letterId, memberEntity));
    }

    //@ApiOperation(value="보낸 편지함 읽기", notes="보낸 편지함 확인.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/letters/sent")
    public Response<?> getSentLetter() {
        MemberEntity memberEntity=memberRepository.findById(1).orElseThrow(()->{
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response("성공", "보낸 쪽지를 불러왔습니다.", letterService.sentLetter(memberEntity));
        }

    //@ApiOperation(value="보낸 쪽지 삭제하기", notes="보낸 쪽지를 삭제합니다.")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/letters/sent/{letterId}")
    public Response<?> deleteSentMessage(@PathVariable("letterId") Integer letterId) {
        MemberEntity memberEntity = memberRepository.findById(1).orElseThrow(()->{
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });

        return new Response<>("삭제 성공", "보낸 쪽지인,"+ letterId +"번 쪽지를 삭제했습니다",letterService.deleteLetterByLetterSender(letterId, memberEntity));
   }

}

*/