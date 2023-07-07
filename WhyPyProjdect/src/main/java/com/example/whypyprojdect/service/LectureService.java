package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.Lecture;
import com.example.whypyprojdect.repository.LectureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public Lecture getLectureById(Integer lectureId) {
        return lectureRepository.findById(lectureId)
                .orElseThrow(() -> new NoSuchElementException("Lecture not found with id: " + lectureId));
    }

}
