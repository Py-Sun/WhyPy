package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Post;
import lombok.Data;

@Data
public class PostDto {
    private int postId;
    private long writerID;
    private String createDate;
    private String updateDate;
    private String title;
    private String contents;
    private String imageName;
    private String imagePath;
    private int recmdNum;
    private int recmdOneDayNum;
    private boolean isAnonymous;
    private int postPublic;
    private String board;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setPostId(int postId) {this.postId = postId;}

    public void setWriterID(long writerID) {this.writerID = writerID;}

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setUpdateDate(String updateDate) {this.updateDate = updateDate; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {this.contents = contents;}

    public void setImageName(String imageName) {this.imageName = imageName;}

    public void setRecmdNum(int recmdNum) {this.recmdNum = recmdNum; }

    public void setRecmdOneDayNum(int recmdOneDayNum) {this.recmdOneDayNum = recmdOneDayNum; }

    public void setIsAnonymous(boolean isAnonymous) {this.isAnonymous = isAnonymous; }

    public void setPostPublic(int postPublic) {this.postPublic = postPublic; }

    public void setBoard(String board) {this.board = board; }

    public PostDto(String title, String contents, String createDate, String updateDate, String imageName, String imagePath, String board) {
        this.title = title;
        this.contents = contents;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.board = board;
    }

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .writerID(writerID)
                .title(title)
                .contents(contents)
                .createDate(createDate)
                .updateDate(updateDate)
                .imageName(imageName)
                .imagePath(imagePath)
                .recmdNum(recmdNum)
                .recmdOneDayNum(recmdOneDayNum)
                .isAnonymous(isAnonymous)
                .postPublic(postPublic)
                .board(board)
                .build();
    }
}