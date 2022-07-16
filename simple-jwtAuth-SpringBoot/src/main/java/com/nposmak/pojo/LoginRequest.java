package com.nposmak.pojo;

import lombok.Data;

/* for AuthController loginUser() request entity*/

@Data
public class LoginRequest {
	
	private String email;
	private String password;
}
