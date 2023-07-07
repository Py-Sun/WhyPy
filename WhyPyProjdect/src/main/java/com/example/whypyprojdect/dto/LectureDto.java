package com.example.whypyprojdect.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class LectureDto {
    private int lectureId;
    private Date viewDate;
    private String url;
    private String title;
    private String thumbnail;
    private String uploader;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate != null ? new Date(viewDate.getTime()) : null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }
}
