package com.example.whypyprojdect.entity;


import com.example.whypyprojdect.dto.LectureDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "lecture_viewDate")
    private LocalDate lectureViewDate;

    @Column(name = "lecture_URL")
    private String lectureUrl;

    @Column(name = "lecture_photoURL")
    private String lecturePhotoUrl;

    @ElementCollection
    @CollectionTable(name = "Lecture_URL", joinColumns = @JoinColumn(name = "lecture_id"))
    @Column(name = "lecture_URL")
    private List<String> lectureUrlList;
}
