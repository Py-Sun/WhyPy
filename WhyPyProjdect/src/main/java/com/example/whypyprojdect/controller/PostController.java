package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.dto.PostDto;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.service.LectureService;
import com.example.whypyprojdect.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostController {
    private final PostService postService;

    @GetMapping("/postList")
    public String getAllPosts(Model model) {
        List<Post> postDtos = postService.getAllPosts();
        model.addAttribute("posts", postDtos);
        return "post-list-page";
    }

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable int postId, Model model) {
        // 클릭한 날짜로 업데이트
        Post postDto = postService.getPostById(postId);
        model.addAttribute("post", postDto);
        return "post-details-page";
    }

    @GetMapping("/createPost")
    public String posting() {
        return "create-post";
    }

    @PostMapping("/createPost")
    public String createPostData(PostDto postDto) {
        Post post = postDto.toEntity();
        Post postEntity = postService.savePostData(post);
        System.out.println(postEntity);
        System.setProperty("server.servlet.context-path", "/postList");
        return "post-list-page";
    }
}