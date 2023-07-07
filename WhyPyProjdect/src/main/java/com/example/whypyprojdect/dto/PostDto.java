package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Post;
import lombok.Data;

@Data
public class PostDto {
    private int postId;
    private String date;
    private String title;
    private String contents;

    // 생성자, getter, setter 등 필요한 메서드 추가

    public void setPostId(int postId) {this.postId = postId;}

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {this.contents = contents;}

    public PostDto(String title, String contents, String date) {
        this.title = title;
        this.contents = contents;
        this.date = date;
    }

    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .title(title)
                .contents(contents)
                .date(date)
                .build();
    }
}