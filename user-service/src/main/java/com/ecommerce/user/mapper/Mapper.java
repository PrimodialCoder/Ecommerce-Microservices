package com.ecommerce.user.mapper;

import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.model.User;

public class Mapper {
    public static UserResponseDto mapToUserResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        return userResponseDto;
    }
}
