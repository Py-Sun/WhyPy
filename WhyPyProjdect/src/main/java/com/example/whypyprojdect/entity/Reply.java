package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Reply")
public class Reply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reply_id")
    private int replyId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "reply_createDate")
    private String createDate;

    @Column(name = "reply_updateDate")
    private String updateDate;

    @Column(name = "reply_comment")
    private String comment;

    @Column(name = "reply_writer_id")
    private long writerId;

    @Column(name = "reply_parent_id")
    private int parentId;

    @Column(name = "reply_is_anonymous")
    private boolean isAnonymous;

    @PrePersist //DB에 INSERT되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @PreUpdate //DB에 UPDATE되기 직전에 실행
    public void updateDate() {
        this.updateDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
