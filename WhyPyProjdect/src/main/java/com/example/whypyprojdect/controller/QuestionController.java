package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.QuestionSolve;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.MemberService;
import com.example.whypyprojdect.service.QuestionService;
import com.example.whypyprojdect.service.QuestionSolveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
        int solveCount = 0;

        List<QuestionDto> questionDtos = questionService.getAllQuestion();
        model.addAttribute("questions", questionDtos);

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        MemberDto memberDto = new MemberDto();
        List<QuestionSolve> qsDto = questionSolveService.getQuestionSolveByMemberId(memberDto.getId());

        if(memberEntity.isPresent())
        {
            memberDto = MemberDto.toMemberDto((memberEntity.get()));
            model.addAttribute("member", memberDto);
            if(qsDto == null || qsDto.isEmpty())
            {
                model.addAttribute("SolveCount", 0);
            }
            else
            {
                solveCount = qsDto.size();
                model.addAttribute("SolveCount", solveCount);
            }
        }

        return "Problem/problem_list";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestionById(@PathVariable int questionId, Model model, HttpSession session) {

        QuestionDto questionDto = questionService.getQuestionById(questionId);
        String questionTitle = questionDto.getTitle();
        String questionContents = questionDto.getContents();
        String questionAnswer = questionDto.getAnswer();
        String questionExample = questionDto.getExample();

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
        }
        return "Problem/problem_answer";
    }

    @RequestMapping(value = "/questions/{questionId}/answer" , method = RequestMethod. POST)
    public void setCheckBox(@RequestParam(value = "checkboxName", required = false) String checkboxValue) {
        if (checkboxValue != null) {
            System.out.println("checkbox is checked");
        } else {
            System.out.println("checkbox is not checked");
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
