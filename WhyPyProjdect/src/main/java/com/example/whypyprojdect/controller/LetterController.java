package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.whypyprojdect.entity.Letter;
import com.example.whypyprojdect.service.LetterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LetterController {
    private final LetterService letterService;
    private final MemberRepository memberRepository;

    @Autowired
    public LetterController(LetterService letterService, MemberRepository memberRepository)
    {
        this.letterService = letterService;
        this.memberRepository = memberRepository;
    }

    // 쪽지 보내기
    @PostMapping("/sendLetter")
    public String sendLetter(@RequestParam String receiverName, @RequestParam String title,
                             @RequestParam String content, HttpSession session) {
        String senderName = (String) session.getAttribute("loginName");
        Optional<MemberEntity> memberOptional = memberRepository.findByMemberName(senderName);
        Optional<MemberEntity> receiverOptional = memberRepository.findByMemberName(receiverName);

        if (memberOptional.isPresent()) {
            MemberEntity member = memberOptional.get();
            long senderId = letterService.getMemberID(member);

            // 받는 사람도 있으면 (추후에 프론트 나오면 친구 리스트와 연동하기)
            if (receiverOptional.isPresent()) {
                MemberEntity receiver = receiverOptional.get();
                long receiverId = letterService.getMemberID(receiver);
                letterService.sendLetter(senderId, receiverId, title, content);
            }
        }

        return "redirect:/sentLetters";
    }

    @GetMapping("/sendLetter")
    public String sendLetterPage(HttpSession session){
        if(session.getAttribute("loginName")== null){
            return "/login";
        }

        return "Letter/letter-send";
    }

    // 받은 쪽지함
    // 중복 코드 함수 처리 해야하는데.. 미래의 내가 하겠징..ㅎ
    @GetMapping("/receivedLetters")
    public String viewReceivedLetters(Model model, HttpSession session) {
        if(session.getAttribute("loginName")== null){
            return "/login";
        }

        String receiverName = (String) session.getAttribute("loginName");
        Optional<MemberEntity> memberOptional = memberRepository.findByMemberName(receiverName);

        if (memberOptional.isPresent()) {
            MemberEntity member = memberOptional.get();
            long receiverId = letterService.getMemberID(member);
            List<Letter> receivedLetters = letterService.getReceivedLetters(receiverId);
            List<String> senderNames = new ArrayList<>();

            for (Letter letter : receivedLetters) {
                Optional<MemberEntity> senderOptional = memberRepository.findById(letter.getSenderId());
                if (senderOptional.isPresent()) {
                    senderNames.add(senderOptional.get().getMemberName());
                } else {
                    senderNames.add("알 수 없음");
                }
            }
            model.addAttribute("senderNames", senderNames);

            model.addAttribute("receivedLetters", receivedLetters);
        }

        return "Letter/letter-receive-list";
    }

    // 보낸 쪽지함
    @GetMapping("/sentLetters")
    public String viewSentLetters(Model model, HttpSession session) {
        if(session.getAttribute("loginName")== null){
            return "/login";
        }

        String senderName = (String) session.getAttribute("loginName");
        Optional<MemberEntity> memberOptional = memberRepository.findByMemberName(senderName);

        if (memberOptional.isPresent()) {
            MemberEntity member = memberOptional.get();
            long senderId = letterService.getMemberID(member);
            List<Letter> sentLetters = letterService.getSentLetters(senderId);
            List<String> receiverNames = new ArrayList<>();
            for (Letter letter : sentLetters) {
                Optional<MemberEntity> senderOptional = memberRepository.findById(letter.getSenderId());
                if (senderOptional.isPresent()) {
                    receiverNames.add(senderOptional.get().getMemberName());
                } else {
                    receiverNames.add("알 수 없음");
                }
            }
            model.addAttribute("receiverNames", receiverNames);
            model.addAttribute("sentLetters", sentLetters);
        }

        return "Letter/letter-send-list";
    }

    // 받은 쪽지 삭제
    @GetMapping("/deleteReceivedLetter/{letterId}")
    public String deleteReceivedLetter(@PathVariable int letterId) {
        letterService.deleteReceivedLetter(letterId);
        return "redirect:/receivedLetters";
    }

    // 보낸 쪽지 삭제
    @GetMapping("/deleteSentLetter/{letterId}")
    public String deleteSentLetter(@PathVariable int letterId) {
        letterService.deleteSentLetter(letterId);
        return "redirect:/sentLetters";
    }
}
