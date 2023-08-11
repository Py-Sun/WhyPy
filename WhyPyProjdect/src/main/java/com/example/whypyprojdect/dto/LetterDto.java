//package com.example.whypyprojdect.dto;
//
//import com.example.whypyprojdect.entity.Letter;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///* 엔티티와 달리 값을 예쁘게 (?) 주고 받는 dto에서는
//title,content,sendername,receivername만 받아서 서버(프론트)에 주면 됨  */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class LetterDto {
//    private String letterTitle;
//    private String letterContent;
//    private String letterSenderName;
//    private String letterReceiverName;
//
//    public static LetterDto toDto(Letter letter) {
//        return new LetterDto(
//                letter.getLetterTitle(),
//                letter.getLetterContent(),
//                letter.getLetterSender().getNickName(),
//                letter.getLetterReceiver().getNickName()
//        );
//    }
//}
