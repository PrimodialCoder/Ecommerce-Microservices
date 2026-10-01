package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto register(UserCreateRequestDto userCreateRequestDto);
    LoginResponseDto login(String email, String password);
    UserResponseDto getUserById(Long id);

}
