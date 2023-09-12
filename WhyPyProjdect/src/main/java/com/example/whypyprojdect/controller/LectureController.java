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

import java.util.*;

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

        List<LectureDto> recmdLectureDto = CosineSimilarity(lectureDto.getTitle(), lectureDtos);

        model.addAttribute("lectures", lectureDtos);

        model.addAttribute("lecture", lectureDto);

        model.addAttribute("recmdLecture", recmdLectureDto);

        return "lecture-details-page";
    }

    public List<LectureDto> CosineSimilarity(String title, List<LectureDto> lectureDtos) {
        Map<Integer, Double> similarityMap = new HashMap<>();
        for (LectureDto lecture : lectureDtos) {
            double sim = similarity(title, lecture.getTitle());
            if(sim != 1.0) similarityMap.put(lecture.getLectureId(), sim);
        }

        List<Map.Entry<Integer, Double>> sortedList = new ArrayList<>(similarityMap.entrySet());
        Collections.sort(sortedList, (entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        List<LectureDto> recmdLectureDto = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Double> entry : sortedList) {
            LectureDto lectureDto = lectureService.getLectureById(entry.getKey());
            recmdLectureDto.add(lectureDto);
            count++;
            if (count >= 3) {
                break;
            }
        }
        return recmdLectureDto;
    }

    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }

        int longerLength = longer.length();
        if (longerLength == 0) return 1.0;
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];

                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}
