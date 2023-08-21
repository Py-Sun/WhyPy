package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.MemberDto;
import com.example.whypyprojdect.entity.MemberEntity;
import com.example.whypyprojdect.entity.Friends;
import com.example.whypyprojdect.exception.NotFoundException;
import com.example.whypyprojdect.repository.FriendsRepository;
import com.example.whypyprojdect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class FriendsService {
    private final FriendsRepository friendsRepository;
    private final MemberRepository memberRepository;

    public List<Friends> getAllFriends() {
        return friendsRepository.findAll();
    }

    public Friends getFriendsById(Integer friendsId) {
        return friendsRepository.findById(friendsId)
                .orElseThrow(() -> new NoSuchElementException("Friends not found with id: " + friendsId));
    }

    public Friends saveFriendsData(Friends friends, long receiverId) {
        friends.setReceiverId(receiverId);
        friends.setState("pending");
        Friends friendsEntity = friendsRepository.save(friends);
        return friendsEntity;
    }

    public void deleteFriendsData(Integer friendsId) {
        friendsRepository.deleteById(friendsId);
    }

    public void updateFriendsData(Integer friendsId, String state) {
        Optional<Friends> friendsOptional = friendsRepository.findById(friendsId);
        Friends friends = friendsOptional.orElseThrow(() -> new NotFoundException("Friends not found"));
        friends.setReceiverId(friendsId);
        friends.setState(state);
        friendsRepository.save(friends);
    }

    public void setSenderID(Friends friends, Object senderName) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberName((String) senderName);
        if(memberEntity.isPresent()) {
            MemberDto member = MemberDto.toMemberDto(memberEntity.get());
            friends.setSenderId(member.getId());
        }
    }
}
