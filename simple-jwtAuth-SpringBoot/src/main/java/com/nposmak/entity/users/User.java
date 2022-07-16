package com.nposmak.entity.users;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {
	@org.springframework.data.annotation.Id
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="sname")
	private String sname;
	
	@Column(name="fname")
	private String fname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="role_id")
	private Role role;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "office_id")
	private Office office;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "department_id")
	private Department department;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "position_id")
	private Position position;

	
	public User() {}


	public User(Role role, String name, String sname, String fname, String email, String password, Office office,
			Department department, Position position) {
		this.role = role;
		this.name = name;
		this.sname = sname;
		this.fname = fname;
		this.email = email;
		this.password = password;
		this.office = office;
		this.department = department;
		this.position = position;
	}


	public boolean userHasRole(String roleName) {
		Role usrRole = this.role;
		if(usrRole.getRoleName().equals(roleName)) {
			return true;
		}
		return false;
	}


	

	
	

	
	

}
