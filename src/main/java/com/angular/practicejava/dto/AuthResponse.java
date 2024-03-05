package com.angular.practicejava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String jwtToken;
    private String username;
}
