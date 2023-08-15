package com.example.whypyprojdect.entity;

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
@Table(name = "Lwatch")
public class LectureWatch {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "lwatch_id")
    private int id;

    @Column(name = "lecture_id")
    private int lectureId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "l_date")
    private String lDate;

    public void setLDate(String lDate) {this.lDate = lDate;}

    @PrePersist
    public void createDate(){
        this.lDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
