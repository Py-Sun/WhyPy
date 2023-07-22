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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        return "full-article-page";
    }

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable int postId, Model model) {
        Post postDto = postService.getPostById(postId);
        model.addAttribute("post", postDto);
        return "post-details-page";
    }

    @GetMapping("/createPost")
    public String posting() {
        return "create-post";
    }

    @PostMapping("/createPost")
    public String createPostData(PostDto postDto, MultipartFile image) throws Exception {
        Post post = postDto.toEntity();
        //postService.savePostData(post);
        postService.createPostData(post, image);
        System.setProperty("server.servlet.context-path", "/postList");
        return "redirect:/postList";
    }

    @GetMapping("/post/{postId}/deletePost")
    public String deletePostById(@PathVariable int postId) {
        postService.deletePostData(postId);
        return "redirect:/postList";
    }

    @GetMapping("/post/{postId}/updatePost")
    public String updatePosting(@PathVariable int postId, Model model) {
        Post postDto = postService.getPostById(postId);
        model.addAttribute("post", postDto);
        return "update-post";
    }

    @PostMapping("/post/{postId}/updatePost")
    public String updatePostById(@PathVariable int postId, @RequestParam String title, @RequestParam String contents) {
        postService.updatePostData(postId, title, contents);
        return "redirect:/postList";
    }
}