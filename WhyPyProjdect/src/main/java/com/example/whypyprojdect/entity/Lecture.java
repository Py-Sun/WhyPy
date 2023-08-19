package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;


@Entity
@Getter
@Table(name = "Lecture")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private int lectureId;

    //@Column(name = "lecture_viewDate")
    //private Date viewDate;

    @Column(name = "lecture_URL")
    private String url;

    @Column(name = "lecture_Title")
    private String title;

    @Column(name = "lecture_Thumbnail")
    private String thumbnail;

    @Column(name = "lecture_Uploader")
    private String uploader;

    @Column(name="lecture_category")
    private int category;

    // 생성자, getter, setter 등 필요한 메서드 추가
    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    //public void setViewDate(Date viewDate) {
    //    this.viewDate = viewDate;
    //}
}