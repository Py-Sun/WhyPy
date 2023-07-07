package com.example.whypyprojdect.entity;

import lombok.AccessLevel;
import lombok.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
// DB에 User 클래스를 바탕으로 테이블이 생성됨
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer Id;
}