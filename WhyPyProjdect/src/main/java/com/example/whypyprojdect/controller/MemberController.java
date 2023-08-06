package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor ///생성자 주입을 위해 선언
//링크를 클릭하는 건 다 get
public class MemberController {
    //@@@--생성자 주입. memberService 필드를 매개변수로 하는 컨트롤 생성자를 만들어 줌.
    //MemberController클래스에 대한 객체를 스프링 빈에 등록할 때 자동적으로 서비스 클래스의 객체를 주입을 받는다.
    //주입 받는다=컨트롤러가 서비스 클래스의 자원(메소드, 필드 등)을 사용할 수 있게 됨
    private final MemberService memberService;

    //회원가입 페이지 출력 요청. 화면을 요청하는 방식으로 컨트롤러 작성
    //회원가입 페이지만 띄워주는 거. 회원가입 정보 입력받은 주소를 받는 것은 다른 코드
    //받아주는 주소 자체가 없으면 404에러가 뜸
    @GetMapping("/member/save")
    public String saveForm() {
        return "save";  // /member/save/ 주소가 요청되면 save.html로 보내주겠다
    }

    //cf. @RequestParam("memberEmail") String memberEmail) >a에 입력받은 값을 a에 저장해달라
    @PostMapping("/member/save")  //save에서 입력받은 회원 가입 정보값들이 dto에 잘 담기게 됨(정리한 코드)
    public String save(@ModelAttribute MemberDto memberDto) {
        System.out.println("MemberController.save"); //여기까지 입력하면 500에러가 뜸. 왜? 이제 post방식으로 보낸 걸 받아주는 주소가 있으니 404에러는 아님
        System.out.println("memberDto = " + memberDto);
        memberService.save(memberDto); //memberService의 save메소드를 미리 정해봄 어떻게 호출할지
        return "home";
    }

    @GetMapping("/member/login")
    public String loginForm(HttpSession session, HttpServletRequest request) {

        // 이전 페이지 URL 세션 저장
        String referer = request.getHeader("Referer");
        session.setAttribute("previousPage", referer);

        System.out.println(referer);
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto memberDto, HttpSession session) {
        MemberDto loginResult = memberService.login(memberDto);
        if (loginResult != null) {
            //login 성공
            session.setAttribute("loginName", loginResult.getMemberName());

            // 바로 이전 페이지로 리다이렉트
            return "redirect:/member/return";
            //return "loginhome";
        } else {
            //login 실패
            return "login";
        }
    }

    // 이전 페이지로 돌아가기
    @GetMapping("/member/return")
    public String returnToPrevious(HttpSession session) {
        String previousPage = (String) session.getAttribute("previousPage");
        System.out.println(previousPage);
        if (previousPage != null && !previousPage.isEmpty()) {
            return "redirect:" + previousPage;
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/member/mypage")
    public String mypageForm(HttpServletRequest request, Model model) {
        HttpSession session= request.getSession();
        if (!session.isNew() && session.getAttribute("loginName") !=null) {
            //새로운 세션이 없고(세션이 유지되어있고) && loginName이라는 setAttribute가 있다면
            return "mypage";
        } else {
            return "login";
        }
    }

    @GetMapping("/member/editInform")
    public String updateForm(HttpSession session, Model model) {
        String myName = (String) session.getAttribute("loginName");
        MemberDto memberDto = memberService.updateForm(myName);
        model.addAttribute("updateMember",memberDto);
        return "editInform";
    }

    @PostMapping("/member/editInform")
    public String update(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:mypage"; //+memberDto.getId();
    }

    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
        //return "home";
    }

    //프로필 정보 보여주기

}


