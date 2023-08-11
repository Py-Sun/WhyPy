package com.example.whypyprojdect.service;

import com.example.whypyprojdect.entity.Follow;
import com.example.whypyprojdect.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;

@Service
public class FollowService {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberService memberService;

    public void save(Long login_id, Long page_id) { // 팔로우
        Follow f = new Follow();
//        Timestamp timestamp;
//        timestamp = new Timestamp(System.currentTimeMillis());

        f.setFollowing(memberService.findById(login_id));
        f.setFollower(memberService.findById(page_id));
       // f.setFollow_date(timestamp);

        followRepository.save(f);
    }
    public void deleteByFollowingIdAndFollowerId(Long id1, Long id2) { // 언팔로우
        followRepository.deleteByFollowingIdAndFollowerId(id2, id1);
    }

    public boolean find(Long id, String memberName) { // 팔로우가 되어있는지를 확인하기위해
        if (followRepository.countByFollowerIdAndFollowingMemberName(id, memberName) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }
}
