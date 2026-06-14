package com.kimsoran.app.auth;

/**
 * 로그인 응답 DTO.
 * accessToken: JWT / tokenType: "Bearer" / expiresIn: 만료 초
 */
public class LoginResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";
    private final long expiresIn;

    public LoginResponse(String accessToken, long expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresIn() { return expiresIn; }
}
