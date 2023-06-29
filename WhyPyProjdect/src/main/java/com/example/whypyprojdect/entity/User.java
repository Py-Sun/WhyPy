package com.example.whypyprojdect.entity;

import lombok.AccessLevel;
import lombok.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)

// DB에 User 클래스를 바탕으로 테이블이 생성됨
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer Id;

    @Column(length = 100)
    private String userName;

    @Column(length = 100)
    private String userPassword;

    @Builder
    public User(Integer Id, String userPassword, String userName) {
        this.Id = Id;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}