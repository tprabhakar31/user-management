package com.user.entity;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@GeneratedValue
	@JdbcTypeCode(SqlTypes.BINARY)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Column(nullable = false)
	private String fullname;
	
	@Column(nullable = false, unique= true)
	private String username;
	
	@Column(nullable = false, unique =true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String refreshToken;
}
