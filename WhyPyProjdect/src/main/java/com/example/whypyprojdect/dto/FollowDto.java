package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Follow;
import com.example.whypyprojdect.entity.MemberEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
public class FollowDto {
    private MemberDto leader;
    private MemberDto follower;

    private FollowDto(MemberDto leader, MemberDto follower) {
        this.leader = leader;
        this.follower = follower;
    }

    protected FollowDto(){}

    public static FollowDto of(MemberDto leader, MemberDto follower){
        return new FollowDto(leader, follower);
    }
}
