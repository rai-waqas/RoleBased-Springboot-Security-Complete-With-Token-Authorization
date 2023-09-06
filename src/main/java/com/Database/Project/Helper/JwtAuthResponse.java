package com.Database.Project.Helper;

import lombok.Data;

import java.util.List;

@Data
public class JwtAuthResponse {
    private final String token;
    private final String type = "Bearer";
    private final String username;
    private final List<String> roles;

    public JwtAuthResponse(String accessToken, String username, List<String> roles) {
        this.token = accessToken;
        this.username = username;
        this.roles = roles;
    }

}
