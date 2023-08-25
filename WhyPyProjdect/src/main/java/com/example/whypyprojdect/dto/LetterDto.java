package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.Letter;
import lombok.Data;

import java.util.Date;

@Data
public class LetterDto {
    private int letterId;
    private long receiverId;
    private long senderId;
    private String title;
    private String content;
    private Date sendDate;

    public void setLetterId(int letterId){this.letterId = letterId;}

    public void setReceiverId(long receiverId){this.receiverId = receiverId;}

    public void setSenderId(long senderId){this.senderId = senderId;}

    public void setTitle(String title){this.title = title;}

    public void setContent(String content){this.content = content;}

    public void SetSendDate(Date sendDate){this.sendDate = sendDate;}

    public void LetterDto() {}

    public Letter toEntity() {
        return Letter.builder()
                .letterId(letterId)
                .receiverId(receiverId)
                .senderId(senderId)
                .title(title)
                .content(content)
                .sendDate(sendDate)
                .build();
    }
}
