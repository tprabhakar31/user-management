package com.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.dto.AuthResponse;
import com.user.dto.LoginRequest;
import com.user.dto.RefreshTokenRequest;
import com.user.dto.SignUpRequest;
import com.user.entity.User;
import com.user.exception.ApiException;
import com.user.repo.UserRepo;
import com.user.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepo userRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	
	public void signup(SignUpRequest request) {
		if(userRepo.existsByUsername(request.getUsername())) {
			throw new RuntimeException("User already exists");
		}
		if(userRepo.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already exists");
		}
		
		User user = new User();
		user.setFullname(request.getFullname());
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		userRepo.save(user);
	}
	
	public AuthResponse login(LoginRequest request) {
		User user = userRepo.findByUsername(request.getUsername())
		.orElseThrow(()-> new ApiException("Invalid User", HttpStatus.UNAUTHORIZED));
		
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new ApiException("invalid username or password", HttpStatus.UNAUTHORIZED);
		}

		String accessToken = jwtUtil.generateAccessToken(user.getUsername());
		String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
		
		user.setRefreshToken(refreshToken);
		userRepo.save(user);
		return new AuthResponse(accessToken, refreshToken);
	}
	
	public AuthResponse refresh(RefreshTokenRequest request) {
		String username = jwtUtil.extractUsername(request.getRefreshToken());
		
		User user = userRepo.findByUsername(username)
		.orElseThrow(()-> new ApiException("Invalid refresh token", HttpStatus.UNAUTHORIZED));
		
		if(!request.getRefreshToken().equals(user.getRefreshToken())) {
			throw new ApiException("Refresh token mismatc", HttpStatus.UNAUTHORIZED);
		}
		
		String newAccessToken = jwtUtil.generateAccessToken(username);
		String newRefreshToken = jwtUtil.generateRefreshToken(username);
		
		user.setRefreshToken(newRefreshToken);
		userRepo.save(user);
		
		return new AuthResponse(newAccessToken, newRefreshToken);
	}
	
	public void logout(RefreshTokenRequest request) {
		
		String username = jwtUtil.extractUsername(request.getRefreshToken());
		
		User user = userRepo.findByUsername(username)
		.orElseThrow(()-> new ApiException("User not found", HttpStatus.UNAUTHORIZED));
		
		user.setRefreshToken(null);
		userRepo.save(user);
	}
	
}
