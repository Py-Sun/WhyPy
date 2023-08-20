package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.entity.Friends;
import com.example.whypyprojdect.service.FriendsService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping("/sendFriends")
    public String sendFriends(HttpSession session) {
        Friends friends = new Friends();
        Object sender = session.getAttribute("loginName");
        friendsService.setSenderID(friends, sender);
        friendsService.saveFriendsData(friends);
        return "redirect:/member/mypage";
    }
}
