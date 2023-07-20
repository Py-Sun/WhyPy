package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questionList")
    public String getAllQuestions(Model model) {
        List<QuestionDto> questionDtos = questionService.getAllQuestionDtos();
        long solvedCount = questionDtos.stream().filter(QuestionDto::getSolved).count();

        model.addAttribute("questions", questionDtos);
        model.addAttribute("solvedCount", solvedCount);

        return "problem_list";
    }

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


}
