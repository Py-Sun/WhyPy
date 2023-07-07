package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LectureController {
    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping("/lecture-list")
    public String lectureListPage(Model model) {
        List<Lecture> lectures = lectureService.getAllLectures();
        model.addAttribute("lectures", lectures);
        return "lecture-list-page";
    }

    @GetMapping("/lectureDetails/{lectureId}")
    public String lectureDetailsPage(@PathVariable Integer lectureId, Model model) {
        Lecture lecture = lectureService.getLectureById(lectureId);
        model.addAttribute("lecture", lecture);
        return "lecture-details-page";
    }
}
