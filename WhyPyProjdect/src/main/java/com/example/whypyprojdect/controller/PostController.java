package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.PostDto;
import com.example.whypyprojdect.dto.RecmdDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.FriendsService;
import com.example.whypyprojdect.service.PostService;
import com.example.whypyprojdect.service.RecmdService;
import com.example.whypyprojdect.service.ReplyService;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class PostController {
    private final PostService postService;
    private final MemberRepository memberRepository;
    private final RecmdService recmdService;
    private final ReplyService replyService;
    private final FriendsService friendsService;

    @GetMapping("/postList")
    public String getAllPosts(Model model) {
        List<Post> postDtos = postService.getAllPosts();
        List<String> memberName = new ArrayList<>();
        for(Post post : postDtos) {
            Optional<MemberEntity> memberEntity = memberRepository.findById(post.getWriterID());
            MemberDto memberDto = new MemberDto();
            if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
            memberName.add(memberDto.getNickName());

            int recmdCountForOneDay = postService.getRecmdCountForOneDay(post.getPostId());
            post.setRecmdOneDayNum(recmdCountForOneDay);
            postService.savePostData(post);
        }

        List<Post> topPosts = postDtos.stream()
                .sorted(Comparator.comparingInt(Post::getRecmdOneDayNum).reversed())
                .limit(3) //상위 3개
                .collect(Collectors.toList());

        model.addAttribute("posts", postDtos);
        model.addAttribute("nicknames", memberName);
        model.addAttribute("topPosts", topPosts);
        return "full-article-page";
    }

    @GetMapping("/postList/{board}")
    public String getPostByBoard(@PathVariable String board, Model model) {
        List<Post> postDtos = postService.getPostByBoard(board);
        List<String> memberName = new ArrayList<>();
        for(Post post : postDtos) {
            Optional<MemberEntity> memberEntity = memberRepository.findById(post.getWriterID());
            MemberDto memberDto = new MemberDto();
            if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));
            memberName.add(memberDto.getNickName());
        }

        model.addAttribute("board", board);
        model.addAttribute("posts", postDtos);
        model.addAttribute("nicknames", memberName);
        return "full-article-page-board";
    }

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable int postId, Model model, HttpSession session) {
        Post postDto = postService.getPostById(postId);
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();
        if(memberEntity.isPresent()) memberDto = MemberDto.toMemberDto((memberEntity.get()));

        if(postDto.getPostPublic() == 1) {
            //친구공개
            List<Friends> friendsDto = friendsService.findFriends(postDto.getWriterID());
            boolean isFriend = false;
            for(Friends friends : friendsDto) {
                if(memberDto.getId() == null) break;
                if(postDto.getWriterID() == memberDto.getId()) {
                    isFriend = true;
                    break;
                }
                if(friends.getReceiverId() == memberDto.getId() || friends.getSenderId() == memberDto.getId()) {
                    isFriend = true;
                    break;
                }
            }
            if(!isFriend) return "temp/public-friends-page";
        }

        if(postDto.getPostPublic() == 2) {
            //나만보기
            if(memberDto.getId() == null || postDto.getWriterID() != memberDto.getId()) return "temp/public-writer-page";
        }
        
        Optional<Recmd> recmdDto = recmdService.getRecmdByPostIdAndMemberId(postId, memberDto.getId());
        List<Reply> replyList = replyService.getRepliesByPostId(postId);
        List<List<Reply>> rereplyList = new ArrayList<>();
        for(Reply reply : replyList) {
            List<Reply> rereply = replyService.getRepliesByParentId(reply.getReplyId());
            rereplyList.add(rereply);
        }

        Optional<MemberEntity> writerEntity = memberRepository.findById(postDto.getWriterID());
        MemberDto writerDto = new MemberDto();
        if(writerEntity.isPresent()) writerDto = MemberDto.toMemberDto((writerEntity.get()));
        String writerName = writerDto.getNickName();

        model.addAttribute("post", postDto);
        model.addAttribute("member", memberDto);
        model.addAttribute("rereply",rereplyList);
        if(recmdDto != null) model.addAttribute("recmd", recmdDto.get());
        else model.addAttribute("recmd", new RecmdDto());
        model.addAttribute("reply", replyList);
        model.addAttribute("writer", writerName);
        return "post_view_page";
    }

    @GetMapping("/createPost")
    public String posting(HttpSession session) {
        if(session.getAttribute("loginName") == null) {
            return "/login";
        }
        //return "writing_page";
        return "temp/create-post";
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

    @GetMapping("/searchPost")
    public String searchPosts(@RequestParam String keyword, Model model) {
        List<Post> searchResults = postService.searchPost(keyword);
        if(searchResults != null && !searchResults.isEmpty())
            model.addAttribute("postList", searchResults);
        return "Search/search_post_page";
    }
}