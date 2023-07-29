package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.PostDto;
import com.example.whypyprojdect.dto.RecmdDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.entity.Recmd;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.MemberService;
import com.example.whypyprojdect.service.PostService;
import com.example.whypyprojdect.service.RecmdService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;
    private final RecmdService recmdService;

    @GetMapping("/postList")
    public String getAllPosts(Model model) {
        List<Post> postDtos = postService.getAllPosts();
        model.addAttribute("posts", postDtos);
        return "full-article-page";
    }

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable int postId, Model model, HttpSession session) {
        Post postDto = postService.getPostById(postId);
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();
        if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
        Optional<Recmd> recmdDto = recmdService.getRecmdByPostIdAndMemberId(postId, memberDto.getId());
        //MemberDto memberDto = memberService.updateForm((String) session.getAttribute("loginName"));
        model.addAttribute("post", postDto);
        model.addAttribute("member", memberDto);
        if(recmdDto != null) model.addAttribute("recmd", recmdDto.get());
        else model.addAttribute("recmd", new RecmdDto());
        return "post-details-page";
    }

    @GetMapping("/createPost")
    public String posting() {
        return "create-post";
    }

    @PostMapping("/createPost")
    public String createPostData(PostDto postDto, MultipartFile image, HttpSession session) throws Exception {
        Post post = postDto.toEntity();
        Object writer = session.getAttribute("loginName");
        postService.setWriterID(post, writer);
        if(!image.isEmpty()) postService.createPostData(post, image);
        else postService.savePostData(post);
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

    @PostMapping("/post/{postId}/recmdPost")
    public ResponseEntity<?> recmdPost(@PathVariable int postId, HttpSession session) {
        Post post = postService.getPostById(postId);
        if (post != null) {
            int recmdNum = post.getRecmdNum();
            post.setRecmdNum(recmdNum + 1);
            postService.savePostData(post);
            Recmd recmd = new Recmd();
            Object member = session.getAttribute("loginName");
            recmdService.setPostID(recmd, postId);
            recmdService.setMemberID(recmd, member);
            recmdService.saveRecmdData(recmd);
            return ResponseEntity.ok("Recmd added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}