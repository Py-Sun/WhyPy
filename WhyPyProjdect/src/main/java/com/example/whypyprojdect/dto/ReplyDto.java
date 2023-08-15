package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Reply;
import lombok.Data;

@Data
public class ReplyDto {
    private int replyId;
    private int postId;
    private String createDate;
    private String updateDate;
    private String comment;
    private long writerId;
    private int parentId;
    private boolean isAnonymous;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setReplyId(int replyId) {this.replyId = replyId;}

    public void setPostId(int postId) {this.postId = postId;}

    public void setCreateDate(String createDate) {this.createDate = createDate;}

    public void setUpdateDate(String updateDate) {this.updateDate = updateDate;}

    public void setComment(String comment) {this.comment = comment;}

    public void setWriterId(long writerId) {this.writerId = writerId;}

    public void setParentId(int parentId) {this.parentId = parentId;}

    public void setIsAnonymous(boolean isAnonymous) {this.isAnonymous = isAnonymous; }

    public ReplyDto(String comment, String createDate, String updateDate) {
        this.comment = comment;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Reply toEntity() {
        return Reply.builder()
                .replyId(replyId)
                .postId(postId)
                .comment(comment)
                .createDate(createDate)
                .updateDate(updateDate)
                .writerId(writerId)
                .parentId(parentId)
                .isAnonymous(isAnonymous)
                .build();
    }
}