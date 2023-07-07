package com.example.whypyprojdect.repository;

import com.example.whypyprojdect.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Integer> {

}
