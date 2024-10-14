package com.example.SocialAuth.controller;

import com.example.SocialAuth.dto.LoginRequest;
import com.example.SocialAuth.dto.LoginResponse;
import com.example.SocialAuth.dto.SignupRequest;
import com.example.SocialAuth.entity.User;
import com.example.SocialAuth.helper.JwtHelper;
import com.example.SocialAuth.response.ApiResponse;
import com.example.SocialAuth.response.ResponseUtil;
import com.example.SocialAuth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,JwtHelper jwtHelper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<List<User>>> signup(@RequestBody SignupRequest request) {
        System.out.println(request);
        userService.signup(request);
        return ResponseEntity.ok(ResponseUtil.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String token = jwtHelper.generateToken(authentication.getName());
        return ResponseEntity.ok(new LoginResponse(request.username(), token));
    }



    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        try {
            List<User> users = userService.findUsers();
            return ResponseEntity.ok(ResponseUtil.success(users));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<User>>> delete(@PathVariable Long id) {
        try {

            userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseUtil.success(null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<User>>> getUser(@PathVariable Long id) {
        try {
            Optional<User> users = userService.findUserById(id);
            return ResponseEntity.ok(ResponseUtil.success(Collections.singletonList(users)));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<User>>> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(ResponseUtil.success(updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ResponseUtil.error(e.getMessage()));
        }
    }
}



