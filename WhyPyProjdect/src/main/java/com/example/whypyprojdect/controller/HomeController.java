package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final PostService postService;
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    //GET, POST 모두
    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session= request.getSession();
        if (!session.isNew() && session.getAttribute("loginName") !=null) {

            String myName = (String) session.getAttribute("loginName");
            System.out.println(myName);
            model.addAttribute("loginName",myName);
        }
        getTopPost(model);
        return "home";
    }

    public void getTopPost(Model model) {
        List<Post> postDtos = postService.getAllPosts();
        for(Post post : postDtos) {
            int recmdCountForOneDay = postService.getRecmdCountForOneDay(post.getPostId());
            post.setRecmdOneDayNum(recmdCountForOneDay);
            postService.savePostData(post);
        }

        List<Post> topPost = postDtos.stream()
                .sorted(Comparator.comparingInt(Post::getRecmdOneDayNum).reversed())
                .limit(1) //상위 3개
                .collect(Collectors.toList());
        model.addAttribute("topPost", topPost);
    }
}
