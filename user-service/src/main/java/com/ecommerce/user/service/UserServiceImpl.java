package com.ecommerce.user.service;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.exception.ResourceAlreadyExistsException;
import com.ecommerce.user.exception.ResourceNotFoundException;
import com.ecommerce.user.mapper.AuthMapper;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.util.JwtUtil;
import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private final AuthMapper authMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthMapper authMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authMapper = authMapper;
    }

    @Override
    public UserResponseDto register(UserCreateRequestDto userCreateRequestDto) {
        if (userRepository.existsByEmail(userCreateRequestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered: " + userCreateRequestDto.getEmail());
        }
        User user = userMapper.toEntity(userCreateRequestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Invalid credentials : email or password !!"));
        Boolean passwordMatches = passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new ResourceNotFoundException("Invalid credentials : email or password !!");
        }
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());
        return authMapper.toLoginResponseDto(user, token);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toDto(user);
    }
}
