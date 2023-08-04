package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Recmd")
public class Recmd {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "recmd_id")
    private int recmdId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "recmd_date")
    private String recmdDate;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate(){
        this.recmdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}