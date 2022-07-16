package com.nposmak.controllers;

import java.util.Objects;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nposmak.entity.users.Department;
import com.nposmak.entity.users.Office;
import com.nposmak.entity.users.Position;
import com.nposmak.entity.users.Role;
import com.nposmak.entity.users.User;
import com.nposmak.pojo.JwtResponse;
import com.nposmak.pojo.LoginRequest;
import com.nposmak.repository.users.DepartRepo;
import com.nposmak.repository.users.OfficeRepo;
import com.nposmak.repository.users.PositRepo;
import com.nposmak.repository.users.RoleRepo;
import com.nposmak.repository.users.UserRepo;
import com.nposmak.security.JwtUtils;
import com.nposmak.security.UserDetailsImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepo userRepo;	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private OfficeRepo officeRepo;
	
	@Autowired
	private DepartRepo departRepo;
	
	@Autowired
	private PositRepo positRepo;
	
//	record RegisterRequest(Long roleId, String name, String sname, String fname, String email, String password, String passwordConfirm, 
//			Long officeId, Long departId, Long positId) {}
	record RegisterRequest(String role, String name, String sname, String fname, String email, String password, String passwordConfirm, 
			String office, String department, String position) {}
	record RegisterResponse(Long userId, String roleName,  String name, String sname, String fname, String email, String password,
			String office, String department, String position, @JsonProperty("register-message") String message) {}
	@PostMapping("/signup")
	public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
		
		if (userRepo.existsByEmail(registerRequest.email)) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user with such email already exists");
		
		if(	!Objects.equals(registerRequest.password, registerRequest.passwordConfirm))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password don't match");
		
//		Role role = roleRepo.findById(registerRequest.roleId).orElseThrow(()-> new EntityNotFoundException("role with id="+
//		registerRequest.roleId+" not exists"));
//		
//		Office office = officeRepo.findById(registerRequest.officeId).orElseThrow(()->new EntityNotFoundException("office with id="
//		+registerRequest.officeId+" not exists"));
//		
//		Department department = departRepo.findById(registerRequest.departId).orElseThrow(()->new EntityNotFoundException("department with id="
//		+registerRequest.departId+" not exists"));
//		
//		Position position = positRepo.findById(registerRequest.positId).orElseThrow(()-> new EntityNotFoundException("position with id="+
//		registerRequest.positId+" not exists"));
		Role role = roleRepo.findByRoleName(registerRequest.role);
		Office office = officeRepo.findByOffName(registerRequest.office);
		Department department = departRepo.findByDepName(registerRequest.department);
		Position position =positRepo.findByPosName(registerRequest.position);
		
		User user = new User(
				role,
				registerRequest.name,
				registerRequest.sname,
				registerRequest.fname,
				registerRequest.email,
				passwordEncoder.encode(registerRequest.password),
				office, department, position);	
		userRepo.save(user);
		
		return new RegisterResponse(
				user.getId(), 
				user.getRole().getRoleName(),
				user.getName(), 
				user.getSname(), 
				user.getFname(),
				user.getEmail(), 
				user.getPassword(), 
				user.getOffice().getOffName(),
				user.getDepartment().getDepName(), 
				user.getPosition().getPosName(), "new user was registered");
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						loginRequest.getEmail(), 
						loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return ResponseEntity.ok(new JwtResponse(token,  userDetails.getId(), userDetails.getName(), userDetails.getSname(), userDetails.getFname(),
				userDetails.getEmail(), userDetails.getOffice(), userDetails.getDepartment(), userDetails.getPosition()));
	}
	
	
	/*Send users credentials to frontend if user allready loggedin*/
	record UserResponse(Long userId, String role, String name, String sname, String fname, String email) {}
	@GetMapping("/auth/user")
	public UserResponse authUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization").substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with email = "+email+" not exist!"));
		System.out.println(email);
//		String[] chunks = token.split("\\.");
//		java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
//		String header = new String(decoder.decode(chunks[0]));
//		String payload = new String(decoder.decode(chunks[1]));
//		System.out.println(payload);
		return new UserResponse(user.getId(), user.getRole().getRoleName(), user.getName(), user.getSname(), user.getFname(), user.getEmail());
	}

	
	
	
}
