package com.example.whypyprojdect.entity;

import com.google.api.client.util.DateTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    @Column(name = "post_date")
    private String date;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_contents")
    private String contents;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss"));
    }
}