package com.example.whypyprojdect.controller;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    //회원가입 페이지 출력 요청

    @GetMapping("/member/save")
    public String saveForm() {
        return "Save";
    }

    @PostMapping("/member/save")  //이메일 입력받은 값 저장
    public String save(@RequestParam("memberEmail") String memberEmail) {
        System.out.println("MemberController.save");
        System.out.println("memberEmail = " + memberEmail); //매개변수 생성
        return "index";
    }

}
