package com.example.whypyprojdect.entity;

import com.example.whypyprojdect.dto.FollowDto;
import com.example.whypyprojdect.dto.MemberDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.*;

import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="follower_id")
    private MemberEntity follower;
    @ManyToOne
    @JoinColumn(name="following_id")
    private MemberEntity following;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Seoul")
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(insertable = false, updatable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    private Date createdAt;

    @Builder
    public Follow(MemberEntity follower, MemberEntity following) {
        this.follower = follower;
        this.following = following;
    }
//
//    public static MemberEntity toFollow(FollowDto followDto) {
//        Follow follow = new Follow();
//        follow.setFollower(followDto.getFollower());
//        follow.setFollowing(followDto.getFollowing());
//        //dto에 담긴 것을 entity로 넘김(변환)
//        //에러가 나거나 값이 생각한 값이 아니면 이 부분에서 문제가 있을 가능성 큼
//        return follow;
//    }
//
//    public static MemberEntity toUpateFollow(FollowDto followDto) {
//        Follow follow = new Follow();
//        follow.setId(followDto.getId());
//        follow.setFollower(followDto.getFollower());
//        follow.setFollowing(followDto.getFollowing());
//        return follow;
//    }

}
