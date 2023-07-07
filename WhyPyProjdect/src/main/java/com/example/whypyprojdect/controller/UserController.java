package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.UserDto;
import com.example.whypyprojdect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")

// 요청에 응답
public class UserController {
    private final UserService userService;

    /*
    @GetMapping("/{str}")
    public String User(@PathVariable String str) {
        UserDto res = userService.UserServiceMethod(str);

        return "hello "+res.getUserStr() ;
    }
     */
}
