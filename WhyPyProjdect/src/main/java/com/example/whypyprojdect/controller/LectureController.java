package com.example.whypyprojdect.controller;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.LectureWatch;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.repository.MemberRepository;
import com.example.whypyprojdect.service.LectureService;
import com.example.whypyprojdect.service.LectureWatchService;
import com.example.whypyprojdect.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class LectureController {
    private final LectureService lectureService;
    private final LectureWatchService lectureWatchService;
    private final MemberRepository memberRepository;
    @Autowired
    public LectureController(LectureService lectureService, MemberRepository memberRepository, LectureWatchService lectureWatchService) {
        this.lectureService = lectureService;
        this.memberRepository = memberRepository;
        this.lectureWatchService = lectureWatchService;
    }

    @GetMapping("/lectures")
    public String getAllLectures(@RequestParam(name = "category", required = false) Integer categoryId,
                                 Model model) {
        List<LectureDto> lectureDtos;

        if (categoryId != null) {
            // 카테고리별 데이터 검색
            lectureDtos = lectureService.getLecturesByCategory(categoryId);
        } else {
            // 없으면 모든 강의 리스트  저장
            lectureDtos = lectureService.getAllLectures();
        }

        model.addAttribute("lectures", lectureDtos);
        return "lecture-list-page";
    }



    @GetMapping("/lectures/{lectureId}")
    public String getLectureById(@PathVariable int lectureId, Model model, HttpSession session) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) session.getAttribute("loginName"));
        // 로그인 되어있으면 클릭한 날짜로 업데이트
        if(memberEntity.isPresent())
        {
            LectureWatch lectureWatch = new LectureWatch();
            Object member= session.getAttribute("loginName");
            lectureWatchService.setLectureId(lectureWatch, lectureId);
            lectureWatchService.setMemberId(lectureWatch, member);
            lectureWatchService.saveOrupdateViewDate(lectureWatch);
        }

        //lectureService.updateViewDate(lectureId, viewDate);

        LectureDto lectureDto = lectureService.getLectureById(lectureId);
        String videoUrl = lectureDto.getUrl();
        String videoId = lectureService.extractVideoId(videoUrl);
        lectureDto.setVideoId(videoId);

        List<LectureDto> lectureDtos = lectureService.getAllLectures();
        model.addAttribute("lectures", lectureDtos);

        model.addAttribute("lecture", lectureDto);

        return "lecture-details-page";
    }
}
