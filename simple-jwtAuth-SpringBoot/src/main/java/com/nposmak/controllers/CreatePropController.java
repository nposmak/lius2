package com.nposmak.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nposmak.entity.proposals.Proposal;
import com.nposmak.entity.users.User;
import com.nposmak.repository.proposals.CbnRepo;
import com.nposmak.repository.proposals.GpaRepo;
import com.nposmak.repository.proposals.GtuRepo;
import com.nposmak.repository.users.UserRepo;
import com.nposmak.security.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CreatePropController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private GpaRepo gpaRepo;
	
	@Autowired
	private GtuRepo gtuRepo;
	
	@Autowired
	private CbnRepo cbnRepo;

	record CreatePropRequest(String gpa,  String gtu, String cbn, String description, List<Long> confirmList) {}
	@PostMapping("/prop/create")
	public void createProposal(@RequestHeader("Authorization") String tokenHeader,  @RequestBody CreatePropRequest body) {
		System.out.println(tokenHeader);
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User with email = "+email+" not exist!"));
		
		
		Proposal proposal = new Proposal();
		Date date = new Date();
		proposal.setPropDate(date);
		proposal.setAuthor(user);
		
		
		System.out.println(body.gpa+"\n"+body.gtu+"\n"+body.cbn);
		System.out.println(body.description);
		body.confirmList.stream().forEach(x ->System.out.println(x));
		
		
		
		//return null;
	}

	
	
	
}
