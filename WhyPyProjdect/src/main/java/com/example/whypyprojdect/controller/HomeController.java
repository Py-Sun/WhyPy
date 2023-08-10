package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Post;
import com.example.whypyprojdect.entity.QuestionSolve;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.PostService;
import com.example.whypyprojdect.service.QuestionService;
import com.example.whypyprojdect.service.QuestionSolveService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final PostService postService;
    private final QuestionService questionService;
    private final MemberRepository memberRepository;
    private final QuestionSolveService questionSolveService;
    public HomeController(PostService postService, QuestionService questionService, MemberRepository memberRepository, QuestionSolveService questionSolveService)
    {
        this.postService = postService;
        this.questionService = questionService;
        this.questionSolveService = questionSolveService;
        this.memberRepository= memberRepository;
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
        getUnsolvedQuestions(model, session);
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

    public void getUnsolvedQuestions(Model model, HttpSession session)
    {
        List<QuestionDto> unsolvedQuestions = new ArrayList<>();
        Map<Integer, Boolean> questionSolveMap = new HashMap<>();

        List<QuestionDto> questionDtos = questionService.getAllQuestion();
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));

        if (memberEntity.isPresent()) {
            MemberDto memberDto = MemberDto.toMemberDto((memberEntity.get()));
            List<QuestionSolve> questionSolveDtos = questionSolveService.getQuestionSolveByMemberId(memberDto.getId());

            for (QuestionSolve qs : questionSolveDtos) {
                questionSolveMap.put(qs.getQuestionId(), qs.isSolved());
            }

            // 풀지 않은 문제들만 리스트에 추가
            for (QuestionDto question : questionDtos) {
                if (!questionSolveMap.containsKey(question.getId()) || !questionSolveMap.get(question.getId())) {
                    unsolvedQuestions.add(question);
                }
            }
        } else {
            // 사용자가 로그인하지 않은 경우 모든 문제 추가
            unsolvedQuestions.addAll(questionDtos);
        }
        // 5개만 담기
        Collections.shuffle(unsolvedQuestions);
        List<QuestionDto> randomUnsolvedQuestions = unsolvedQuestions.stream()
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("unsolvedQuestions", randomUnsolvedQuestions);
    }
}
