package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //@@@--생성자 주입. 자동적으로 서비스 클래스의 객체를 주입을 받는다. 메소드등을 사용할 수 있게 됨
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm() {
        return "Save";
    }

    @PostMapping("/member/save")  //입력받은 회원 가입 정보값들이 dto에 잘 담기게 됨(정리한 코드)
    public String save(@ModelAttribute MemberDto memberDto) {
        System.out.println("MemberController.save");
        System.out.println("memberDto = " + memberDto);
        memberService.save(memberDto); //memberService의 save메소드를 미리 정해봄 어떻게 호출할지
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            //login 성공
            session.setAttribute("loginName", loginResult.getMemberName());
            return "main";
        } else {
            //login 실패
            return "login";
        }
    }
}

    /* (정리 전 코드_의미 파악 위해 놔둠)
    public String save(@RequestParam("memberEmail") String memberEmail,
                       @RequestParam("memberPassword") String memberPassword,
                       @RequestParam("memberName") String memberName) {
            System.out.println("MemberController.save");
            System.out.println("memberEmail = " + memberEmail); //매개변수 생성
            return "home";
        }
        */