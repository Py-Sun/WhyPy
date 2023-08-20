package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Friends;
import lombok.Data;

@Data
public class FriendsDto {
    private int friendsId;
    private long senderId;
    private long receiverId;
    private String state;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setfriendsId(int friendsId) {this.friendsId = friendsId;}

    public void setSenderId(long senderId) {this.senderId = senderId;}

    public void setReceiverId(long receiverId) {this.receiverId = receiverId;}

    public void setState(String state) {this.state = state;}

    public FriendsDto() {
    }

    public Friends toEntity() {
        return Friends.builder()
                .friendsId(friendsId)
                .senderId(senderId)
                .receiverId(receiverId)
                .state(state)
                .build();
    }
}