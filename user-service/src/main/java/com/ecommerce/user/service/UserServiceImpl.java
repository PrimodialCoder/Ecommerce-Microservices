package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponseDto register(UserCreateRequestDto userCreateRequestDto) {
        if (userRepository.existsByEmail(userCreateRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = new User();
        user.setName(userCreateRequestDto.getName());
        user.setEmail(userCreateRequestDto.getEmail());
        user.setPassword(userCreateRequestDto.getPassword());
        user.setPhone(userCreateRequestDto.getPhone());
        User savedUser = userRepository.save(user);
        UserResponseDto responseDto=  new UserResponseDto();
        responseDto.setId(savedUser.getId());
        responseDto.setName(savedUser.getName());
        responseDto.setEmail(savedUser.getEmail());
        responseDto.setPhone(savedUser.getPhone());
        responseDto.setRole(savedUser.getRole());
        responseDto.setCreatedAt(savedUser.getCreatedAt());
        responseDto.setUpdatedAt(savedUser.getUpdatedAt());
        return responseDto;
    }

    @Override
    public LoginResponseDto login(String email, String password) {
        return null;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return null;
    }
}
