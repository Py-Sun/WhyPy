package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.service.LectureService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/lectures")
    public String getAllLectures(Model model) {
        List<LectureDto> lectureDtos = lectureService.getAllLectures();
        model.addAttribute("lectures", lectureDtos);
        return "lecture-list-page";
    }

    @GetMapping("/lectures/{lectureId}")
    public String getLectureById(@PathVariable int lectureId, Model model) {
        LectureDto lectureDto = lectureService.getLectureById(lectureId);
        model.addAttribute("lecture", lectureDto);
        return "lecture-details-page";
    }
}
