package com.example.whypyprojdect.service;

import com.example.whypyprojdect.dto.UserDto;
import com.example.whypyprojdect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@RequiredArgsConstructor

// 비즈니스 로직 구현 (데이터 생성, 표시, 저장, 변경하는 부분)
public class UserService {
    private final UserRepository userRepository;

    public UserDto UserServiceMethod(String str) {
        UserDto userDto = new UserDto(str);

        return userDto;
    }
}
