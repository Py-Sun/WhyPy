package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.dto.QuestionDto;
import com.example.whypyprojdect.service.LectureService;
import com.example.whypyprojdect.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final QuestionService questionService;
    private final LectureService lectureService;
    public SearchController(QuestionService questionService, LectureService lectureService){
        this.questionService = questionService;
        this.lectureService = lectureService;
    }
    @GetMapping("/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model)
    {
        List<QuestionDto> questionDtoList = questionService.searchQuestion(keyword);
        if(questionDtoList != null && !questionDtoList.isEmpty())
            model.addAttribute("questionList", questionDtoList);

        List<LectureDto> lectureDtoList = lectureService.searchLecture(keyword);
        if(lectureDtoList != null && !lectureDtoList.isEmpty())
            model.addAttribute("lectureDtoList", lectureDtoList);
        return "Search/search_page";
    }
}
