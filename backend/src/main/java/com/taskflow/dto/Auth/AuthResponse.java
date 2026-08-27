package com.taskflow.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
	// Short-lived token (15 minutes) - use for API requests
	private String accessToken;

	// Long-lived token (7 days) - use to refresh access token when expired
	private String refreshToken;

	// Token type (always "Bearer" for OAuth 2.0 compatibility)
	private String tokenType = "Bearer";

	// Access token expiration time in seconds
	private Long expiresIn = 900L;  // 15 minutes

	// Constructor for backward compatibility with single token
	public AuthResponse(String token) {
		this.accessToken = token;
		this.tokenType = "Bearer";
		this.expiresIn = 900L;
	}
}
