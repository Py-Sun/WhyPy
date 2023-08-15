package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.FollowDto;
import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.Follow;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Question;
import com.example.whypyprojdect.entity.QuestionSolve;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.FollowRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowService {
    @Autowired
    private final FollowRepository followRepository;
    @Autowired
    private final MemberRepository memberRepository;
//    @Autowired
//    private final FollowDto followDto;

//    public FollowDto findById(Long id) {
//        Optional<Follow> optionalFollow = followRepository.findById(id);
//        if (optionalFollow.isPresent()) {
//            Follow follow = optionalFollow.get();
//            FollowDto followDto = FollowDto.toFollowDto(follow);
//            return followDto;
//        } else {
//            return null;
//        }
//    }
    public void follow(String fromId, String toId) {
        Follow follow = Follow.builder()
                .follower(memberRepository.findByMemberName(fromId).get())
                .following(memberRepository.findByMemberName(toId).get())
                .build();
        followRepository.save(follow);
    }


//    Optional<Question> questionOptional = questionRepository.findById(questionId);
//    Question question = questionOptional.orElseThrow(() -> new NotFoundException("Question not found"));

//    @Transactional
//    public void follow(int fromUserId, int toUserId){
//        try {
//            followRepositoy.mFollow(fromUserId,toUserId);
//        } catch (Exception e){
//            throw new CustomApiException("이미 팔로우 했습니다.");
//        }
//
//    }
//
//    @Transactional
//    public void unfollow(int fromUserId, int toUserId){
//        followRepositoy.mUnFollow(fromUserId,toUserId);
//    }
}