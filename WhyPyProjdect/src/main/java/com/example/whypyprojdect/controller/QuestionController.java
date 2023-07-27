package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public String getAllQuestions(Model model) {
        List<QuestionDto> questionDtos = questionService.getAllQuestion();
        long solvedCount = questionDtos.stream().filter(QuestionDto::getSolved).count();

        model.addAttribute("questions", questionDtos);
        model.addAttribute("solvedCount", solvedCount);

        return "problem_list";
    }

    @GetMapping("/questions/{questionId}")
    public String getQuestionById(@PathVariable int questionId, Model model) {

        QuestionDto questionDto = questionService.getQuestionById(questionId);
        String questionTitle = questionDto.getTitle();
        String questionContents = questionDto.getContents();
        String questionAnswer = questionDto.getAnswer();
        String questionExample = questionDto.getExample();

        model.addAttribute("question", questionDto);

        return "problem_solving";
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
