package com.example.SocialAuth.dto;

import com.example.SocialAuth.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Username cannot be blank")
        String username,

        Role role,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password) {
}
