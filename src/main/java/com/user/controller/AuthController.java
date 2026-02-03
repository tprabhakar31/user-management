package com.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.dto.AuthResponse;
import com.user.dto.LoginRequest;
import com.user.dto.RefreshTokenRequest;
import com.user.dto.SignUpRequest;
import com.user.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody SignUpRequest request){
		authService.signup(request);
		return ResponseEntity.ok("User registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		AuthResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request){
		AuthResponse response = authService.refresh(request);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
		authService.logout(request);
		return ResponseEntity.ok("Logout successfully");
	}
}
