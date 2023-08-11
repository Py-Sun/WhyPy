package com.example.whypyprojdect.entity;

import com.example.whypyprojdect.dto.FollowDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.security.Timestamp;

@Data
@Entity
@org.hibernate.annotations.DynamicUpdate
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "following")
    MemberEntity following;

    @ManyToOne
    @JoinColumn(name = "follower")
    MemberEntity follower;

    Timestamp follow_date;
}