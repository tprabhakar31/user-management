package com.user.dto;

import lombok.Data;

@Data
public class SignUpRequest {

	private String fullname;
	
	private String username;
	
	private String email;
	
	private String password;
}
