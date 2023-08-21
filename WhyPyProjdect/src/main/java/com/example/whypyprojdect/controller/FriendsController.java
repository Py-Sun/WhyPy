package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.FriendsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class FriendsController {
    private final FriendsService friendsService;
    private final MemberRepository memberRepository;

    @GetMapping("/sendFriends")
    public String sendFriends(HttpSession session) {
        Friends friends = new Friends();
        Object sender = session.getAttribute("loginName");
        friendsService.setSenderID(friends, sender);
        friendsService.saveFriendsData(friends);
        return "redirect:/member/mypage";
    }

    @GetMapping("/profile/{memberId}")
    public String getProfileById(@PathVariable long memberId, Model model) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(memberId);
        MemberDto memberDto = new MemberDto();
        if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
        model.addAttribute("member", memberDto);
        return "temp/profile";
    }
}
