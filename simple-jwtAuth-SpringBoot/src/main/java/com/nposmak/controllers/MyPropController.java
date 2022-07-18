package com.nposmak.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nposmak.entity.proposals.Cbn;
import com.nposmak.entity.proposals.Gpa;
import com.nposmak.entity.proposals.Gtu;
import com.nposmak.entity.proposals.PropStatus;
import com.nposmak.entity.proposals.Proposal;
import com.nposmak.entity.users.User;
import com.nposmak.exceptions.UserEmailNotFoundException;
import com.nposmak.repository.proposals.ProposalRepo;
import com.nposmak.repository.users.UserRepo;
import com.nposmak.security.JwtUtils;

@RestController
@RequestMapping("/sec")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MyPropController {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ProposalRepo propRepo;	
	
	@Autowired
	private UserRepo userRepo;
	
	record MyPropResponse(Long id, User author, Date date, Gpa gpa, Gtu gtu, Cbn cbn, String description, /**List<PropConfirm> confirmlist,*/ PropStatus status) {}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myprops")
	public List<MyPropResponse> getProps(@RequestHeader("Authorization") String tokenHeader) throws Exception {
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		
		List<Proposal> propList = propRepo.findAllByAuthor(user);
		List<MyPropResponse> response = new ArrayList<MyPropResponse>();
		
		propList.stream().forEach(prop->response.add(new MyPropResponse(
				prop.getId(),
				prop.getAuthor(),
				prop.getPropDate(),
				prop.getGpa(),
				prop.getGtu(),
				prop.getCbn(),
				prop.getDescription(),
				prop.getStatus()
//				prop.getConfirmList()
				)));

		return response;
	}

}
