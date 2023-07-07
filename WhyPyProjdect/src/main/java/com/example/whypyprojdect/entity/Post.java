package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    private Date date;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_contents")
    private String contents;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.date = new Date();
    }
}