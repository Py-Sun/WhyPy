package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Follow;
import com.example.whypyprojdect.entity.MemberEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowDto {

    private Long follower;
    private Long following;

//    @Builder //처음 초기화할 때만 사용. 이미 초기화 되면 다음에는 사용 불가.
//    public FollowDto(Long follower, Long following) {
//        this.follower = follower;
//        this.following = following;
//    }
//
//    public static FollowDto toFollowDto(Follow follow) {
//        FollowDto followDto = new FollowDto();
//        follow.setId(follow.getId());
//        FollowDto.setFollowing(Follow.getFollowing());
//        FollowDto.setFollower(Follow.getFollower());
//        return FollowDto;
//    }

}