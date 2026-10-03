package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.model.User;

public interface UserService {

    UserResponseDto register(UserCreateRequestDto userCreateRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    UserResponseDto getUserById(Long id);

}
