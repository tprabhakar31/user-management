package com.user.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {
	private Instant timeStamp;
	private int status;
	private String error;
	private String message;
}
