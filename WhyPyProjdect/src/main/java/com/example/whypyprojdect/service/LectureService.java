package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.LectureDto;
import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.repository.LectureRepository;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.whypyprojdect.exception.NotFoundException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public List<LectureDto> getAllLectures() {
        List<Lecture> lectures = lectureRepository.findAll();
        List<LectureDto> lectureDtos = new ArrayList<>();

        for (Lecture lecture : lectures) {
            if (isDataMissing(lecture)) {
                fetchYouTubeData(lecture);
                lectureRepository.save(lecture);
            }
            lectureDtos.add(convertToDto(lecture));
        }

        return lectureDtos;
    }

    public LectureDto getLectureById(int lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        Lecture lecture = lectureOptional.orElseThrow(() -> new NotFoundException("Lecture not found"));

        return convertToDto(lecture);
    }

    private LectureDto convertToDto(Lecture lecture) {
        LectureDto lectureDto = new LectureDto();
        lectureDto.setLectureId(lecture.getLectureId());
        lectureDto.setViewDate(lecture.getViewDate());
        lectureDto.setUrl(lecture.getUrl());
        lectureDto.setTitle(lecture.getTitle());
        lectureDto.setThumbnail(lecture.getThumbnail());
        lectureDto.setUploader(lecture.getUploader());

        return lectureDto;
    }

    private boolean isDataMissing(Lecture lecture) {
        // lecture_title 필드를 기준으로 데이터 누락 확인
        return lecture.getTitle() == null || lecture.getTitle().isEmpty();
    }

    // 유튜브 API 이용하여 데이터 가져오기
    private void fetchYouTubeData(Lecture lecture) {
        String url = lecture.getUrl();

        // 유튜브 API 호출 및 데이터 가져오기
        try {
            String videoId = extractVideoId(url);

            // YouTube API 클라이언트 초기화
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
            YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, null)
                    .setApplicationName("WhypyProject")
                    .build();

            // 동영상 검색
            YouTube.Search.List searchList = youtube.search().list("snippet");
            searchList.setKey("AIzaSyCapIbMXCT6-UUnwBA3Zjm2_poxrCaQ1yU"); // YouTube API 키
            searchList.setQ(videoId);
            searchList.setType("video");
            searchList.setMaxResults(1L);
            SearchListResponse searchResponse = searchList.execute();
            List<SearchResult> searchResults = searchResponse.getItems();

            // 제목, 썸네일, 업로더 정보 가져오기
            if (searchResults != null && searchResults.size() > 0) {
                SearchResult searchResult = searchResults.get(0);
                String title = searchResult.getSnippet().getTitle();
                String thumbnailUrl = searchResult.getSnippet().getThumbnails().getDefault().getUrl();
                String uploader = searchResult.getSnippet().getChannelTitle();

                // 가져온 정보 Lecture 엔티티에 설정
                lecture.setTitle(title);
                lecture.setThumbnail(thumbnailUrl);
                lecture.setUploader(uploader);
            }
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            // 오류가 났을 경우
            lecture.setTitle("API 오류");
            lecture.setThumbnail("API 오류");
            lecture.setUploader("API 오류");
        }
    }

    public String extractVideoId(String videoUrl) {
        String videoId = "";

        if (videoUrl != null && !videoUrl.isEmpty()) {
            String[] urlParts = videoUrl.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandIndex = videoId.indexOf('&');
                if (ampersandIndex != -1) {
                    videoId = videoId.substring(0, ampersandIndex);
                }
            }
        }

        return videoId;
    }

    public void updateViewDate(int lectureId, Date viewDate) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        Lecture lecture = lectureOptional.orElseThrow(() -> new NotFoundException("Lecture not found"));

        lecture.setViewDate(viewDate);
        lectureRepository.save(lecture);
    }
}
