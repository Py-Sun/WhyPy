package com.example.whypyprojdect.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter

// 레이어간 통신되는 클래스 작성
public class UserDto {
    private String UserStr;

    public UserDto(String userStr) {
        this.UserStr = userStr;
    }
}
