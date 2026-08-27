package com.taskflow.controller;

import com.taskflow.dto.Auth.AuthResponse;
import com.taskflow.dto.Auth.LoginRequest;
import com.taskflow.dto.Auth.RefreshTokenRequest;
import com.taskflow.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Login endpoint - returns both access and refresh tokens
     *
     * Access Token: Valid for 15 minutes, used for API requests
     * Refresh Token: Valid for 7 days, used to get new access tokens
     *
     * ENTERPRISE SECURITY: Token expiration prevents long-term compromise
     */
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        // Authenticate user with provided credentials
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        ));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate short-lived access token (15 minutes)
        String accessToken = jwtService.generateAccessToken(userDetails);

        // Generate long-lived refresh token (7 days)
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(900L)  // 15 minutes in seconds
                .build();
    }

    /**
     * Refresh token endpoint - get new access token without re-logging in
     *
     * This is THE KEY to enterprise security:
     * - User doesn't need to login again for 7 days
     * - But compromised access token is only valid for 15 minutes
     * - Compromised refresh token can be revoked immediately
     *
     * Flow:
     * 1. Access token expires after 15 minutes
     * 2. Client calls this endpoint with refresh token
     * 3. Server validates refresh token hasn't expired (7 days)
     * 4. Server returns new access token (valid for 15 more minutes)
     * 5. Client continues using new access token
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            String refreshToken = request.getRefreshToken();

            // Validate refresh token
            if (!jwtService.isRefreshTokenValid(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }

            // Extract username from refresh token
            String username = jwtService.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Generate new access token
            String newAccessToken = jwtService.generateAccessToken(userDetails);

            return ResponseEntity.ok(AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)  // Keep the same refresh token
                    .tokenType("Bearer")
                    .expiresIn(900L)  // 15 minutes
                    .build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Logout endpoint (optional but important for enterprise apps)
     *
     * Note: With stateless JWT, true logout requires:
     * 1. Token blacklist (store revoked tokens in Redis/Cache)
     * 2. Or simply delete refresh token on client side
     *
     * This endpoint can delete user's refresh token from database
     * to prevent further token refreshes
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // In production, you would:
        // 1. Get current user from SecurityContextHolder
        // 2. Mark refresh token as revoked in database
        // 3. Clear any other session data

        return ResponseEntity.ok("Logged out successfully. Please delete tokens from client.");
    }
}


