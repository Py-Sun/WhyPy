package com.example.whypyprojdect.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    //GET, POST 모두
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session= request.getSession();
        if (!session.isNew() && session.getAttribute("loginName") !=null) {

            String myName = (String) session.getAttribute("loginName");
            System.out.println(myName);
            model.addAttribute("loginName",myName);
        }
        return "home";
    }

}
