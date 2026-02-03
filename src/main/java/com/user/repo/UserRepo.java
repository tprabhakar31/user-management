package com.user.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.User;

public interface UserRepo extends JpaRepository<User, UUID> {
	
	public Optional<User> findByUsername(String username);
	
	public boolean existsByUsername(String username);
	
	public boolean existsByEmail(String email);
}
