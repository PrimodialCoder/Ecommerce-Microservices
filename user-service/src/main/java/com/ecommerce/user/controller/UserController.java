package com.ecommerce.user.controller;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.LoginResponseDto;
import com.ecommerce.user.dto.UserCreateRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> Register(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userCreateRequestDto));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> Login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
