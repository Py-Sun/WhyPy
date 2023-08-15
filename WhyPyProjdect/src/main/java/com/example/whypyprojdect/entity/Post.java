package com.example.whypyprojdect.entity;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Post")
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private int postId;

    @Column(name = "post_writerID")
    private long writerID;

    @Column(name = "post_createDate")
    private String createDate;

    @Column(name = "post_updateDate")
    private String updateDate;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_contents")
    private String contents;

    @Column(name = "post_image_name")
    private String imageName;

    @Column(name = "post_image_path")
    private String imagePath;

    @Column(name = "post_recmdNum")
    private int recmdNum;

    @Column(name = "post_recmdOneDayNum")
    private int recmdOneDayNum;

    @Column(name = "post_is_anonymous")
    private boolean isAnonymous;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.recmdNum = 0;
        this.recmdOneDayNum = 0;
    }

    @PreUpdate //DB에 UPDATE되기 직전에 실행
    public void updateDate(){
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}