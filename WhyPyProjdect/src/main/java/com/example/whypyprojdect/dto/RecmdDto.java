package com.example.whypyprojdect.dto;

import lombok.Data;
import com.example.whypyprojdect.entity.Recmd;

@Data
public class RecmdDto {
    private int recmdId;
    private int postId;
    private long memberId;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setRecmdId(int recmdId) {this.recmdId = recmdId;}

    public void setPostId(int postId) {this.postId = postId;}

    public void setMemberId(long memberId) {this.memberId = memberId;}

    public RecmdDto(int postId, long memberId) {
        this.postId = postId;
        this.memberId = memberId;
    }

    public Recmd toEntity() {
        return Recmd.builder()
                .recmdId(recmdId)
                .postId(postId)
                .memberId(memberId)
                .build();
    }
}