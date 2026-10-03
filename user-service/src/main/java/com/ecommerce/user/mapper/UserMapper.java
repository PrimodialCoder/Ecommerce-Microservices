package com.ecommerce.user.mapper;

import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserCreateRequestDto userCreateRequestDto){
        return User.builder()
                .name(userCreateRequestDto.getName())
                .email(userCreateRequestDto.getEmail())
                .phone(userCreateRequestDto.getPhone())
                .password(passwordEncoder.encode(userCreateRequestDto.getPassword()))
                .role("ROLE_USER") // default role
                .build();
    }

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }




}
