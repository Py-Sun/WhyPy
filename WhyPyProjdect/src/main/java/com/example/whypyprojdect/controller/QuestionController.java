package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.dto.QuestionSolveDto;
import com.example.whypyprojdect.entity.*;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.MemberService;
import com.example.whypyprojdect.service.QuestionService;
import com.example.whypyprojdect.service.QuestionSolveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final QuestionSolveService questionSolveService;

    @Autowired
    public QuestionController(QuestionService questionService, MemberRepository memberRepository, MemberService memberService, QuestionSolveService questionSolveService) {
        this.questionService = questionService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.questionSolveService = questionSolveService;
    }

    @GetMapping("/questions")
    public String getAllQuestions(Model model, HttpSession session) {
        List<QuestionDto> questionDtos = questionService.getAllQuestion();

        model.addAttribute("questions", questionDtos);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));

        if(memberEntity.isPresent())
        {
            MemberDto memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);

            long solveCount = questionSolveService.getQuestionSolveSolvedCountByMemberId(memberDto.getId());
            model.addAttribute("SolveCount", solveCount);

            List<QuestionSolve> questionSolveDtos = questionSolveService.getQuestionSolveByMemberId(memberDto.getId());
            model.addAttribute("isSolved", questionSolveDtos);
        }

        return "Problem/problem_list";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestionById(@PathVariable int questionId, Model model, HttpSession session) {

        QuestionDto questionDto = questionService.getQuestionById(questionId);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();

        model.addAttribute("question", questionDto);

        if(memberEntity.isPresent()) {
            memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);
        }

        return "Problem/problem_solving";
    }

    @GetMapping("/questions/{questionId}/answer")
    public String getQuestionAnswer(@PathVariable int questionId, Model model, HttpSession session) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        model.addAttribute("question", questionDto);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();

        if(memberEntity.isPresent()) {
            memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);

            // 해당 질문과 회원에 대한 QuestionSolve 조회
            Optional<QuestionSolve> questionSolveOpt = questionSolveService.getQuestionSolveBySolveIdAndMemberId(questionId, memberDto.getId());

            // qsolve 값이 존재하는 경우, 해당 값에 따라 체크박스 초기 상태를 결정
            boolean isSolved = questionSolveOpt.isPresent() && questionSolveOpt.get().isSolved();
            model.addAttribute("qsolve", isSolved);
        }
        return "Problem/problem_answer";
    }

    @PostMapping("/questions/{questionId}/answer")
    public ResponseEntity<?> saveQuestion(@PathVariable int questionId, @RequestParam boolean qSolved, HttpSession session) {
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        if (questionDto != null) {
            QuestionSolve questionSolve = new QuestionSolve();
            Object member = session.getAttribute("loginName");
            //System.out.println("questionId" + questionId);
            questionSolveService.setQuestionID(questionSolve, questionId);
            questionSolveService.setMemberID(questionSolve, member);
            questionSolveService.saveOrUpdateSolveData(questionSolve, qSolved);
            return ResponseEntity.ok("QuestionSolve added successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


/*``````
    @GetMapping("/questionList/filtered")
    public String getFilteredQuestions(
            @RequestParam(defaultValue = "false") boolean isSolved,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer level,
            Model model) {

        // level이 null인 경우에 대한 처리
        Integer levelValue = (level != null) ? level : -1;
        Integer categoryValue = (category!=null)? category : -1;

        List<QuestionDto> filteredQuestions = questionService.getFilteredQuestionDtos(isSolved, categoryValue, levelValue);
        long solvedCount = filteredQuestions.stream().filter(QuestionDto::getSolved).count();

        model.addAttribute("questions", filteredQuestions);
        model.addAttribute("solvedCount", solvedCount);

        return "problem_list";
    }
*/

}
