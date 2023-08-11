package com.example.whypyprojdect.controller;

import ch.qos.logback.core.model.Model;
import com.example.whypyprojdect.repository.FollowRepository;
import com.example.whypyprojdect.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    private final FollowRepository followRepository;

    @RequestMapping(path = "/member/follow")
    public String follow(HttpServletRequest request, Model model) throws Exception {

        String l = request.getParameter("user_id");
        String p = request.getParameter("page_id");

        Long login_id = Long.parseLong(l);
        Long page_id = Long.parseLong(p);

        followService.save(login_id, page_id);

        return "follow";
       //String redirect_url = "redirect:/member/follow/" + page_id;
       //return redirect_url;

    }

    @RequestMapping("/member/unfollow")
    public String unfollow(HttpServletRequest request, Model model) throws Exception {
        String l = request.getParameter("user_id");
        String p = request.getParameter("page_id");

        Long login_id = Long.parseLong(l);
        Long page_id = Long.parseLong(p);

        followService.deleteByFollowingIdAndFollowerId(page_id, login_id);

        String redirect_url = "redirect:/follow/" + page_id;
        return redirect_url;
    }

}




//    @GetMapping("/member/follow")
//    public String goFollowform (){
//        return "follow";
//}
//
//    @PostMapping("/member/follow")
//    public String saveFollow(HttpServletRequest request, Model model) throws Exception {
//    String l = request.getParameter("user_id");
//    String p = request.getParameter("page_id");
//
//    Long login_id = Long.parseLong(l);
//    Long page_id = Long.parseLong(p);
//
//    followService.save(login_id, page_id);
//
//   String redirect_url = "redirect:/follow/" + page_id;
//   return redirect_url;
//
//    }