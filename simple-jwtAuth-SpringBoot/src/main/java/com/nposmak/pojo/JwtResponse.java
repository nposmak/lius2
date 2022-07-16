package com.nposmak.pojo;

import lombok.Data;

/* for AuthController loginUser() response*/

@Data
public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String name;
	private String sname;
	private String fname;
	private String email;
	private String office;
	private String department;
	private String position;
	
	public JwtResponse(String token,  Long id, String name, String sname, String fname, String email, String office, String department, String position) {
		this.token = token;
		this.id = id;
		this.name = name;
		this.sname = sname;
		this.fname = fname;
		this.email = email;
		this.office = office;
		this.department = department;
		this.position = position;
	}
	
	
}
