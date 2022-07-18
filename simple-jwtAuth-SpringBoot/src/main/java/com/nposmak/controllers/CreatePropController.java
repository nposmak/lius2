package com.nposmak.controllers;

import java.util.ArrayList;
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

import com.nposmak.entity.proposals.Cbn;
import com.nposmak.entity.proposals.Gpa;
import com.nposmak.entity.proposals.Gtu;
import com.nposmak.entity.proposals.PropConfirm;
import com.nposmak.entity.proposals.PropStatus;
import com.nposmak.entity.proposals.Proposal;
import com.nposmak.entity.users.User;
import com.nposmak.exceptions.StatusNotFoundException;
import com.nposmak.exceptions.UserEmailNotFoundException;
import com.nposmak.exceptions.UserIdNotFoundException;
import com.nposmak.repository.proposals.CbnRepo;
import com.nposmak.repository.proposals.GpaRepo;
import com.nposmak.repository.proposals.GtuRepo;
import com.nposmak.repository.proposals.PropStatusRepo;
import com.nposmak.repository.proposals.ProposalRepo;
import com.nposmak.repository.users.UserRepo;
import com.nposmak.security.JwtUtils;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CreatePropController {
	
	@Autowired
	private ProposalRepo propRepo;
	
	@Autowired
	private PropStatusRepo propStatusRepo;
	
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
	public void createProposal(@RequestHeader("Authorization") String tokenHeader,  @RequestBody CreatePropRequest body) throws Exception {
		System.out.println("TOKEN = "+tokenHeader);
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		/** new proposal status id = 1*/
		Long id=(long) 1;
		PropStatus status = propStatusRepo.findById(id).orElseThrow(()-> new StatusNotFoundException("Status not found!"));		
		Proposal proposal = new Proposal();
		Date date = new Date();
		proposal.setPropDate(date);
		proposal.setAuthor(user);
		proposal.setStatus(status);

		Gpa gpa = gpaRepo.findByGpaName(body.gpa);
		Gtu gtu = gtuRepo.findByGtuName(body.gtu);
		Cbn cbn = cbnRepo.findByCbnName(body.cbn);
		
		proposal.setGpa(gpa);
		proposal.setGtu(gtu);
		proposal.setCbn(cbn);
		proposal.setDescription(body.description);
		
		for(Long c : body.confirmList) {
			User coordinator = userRepo.findById(c).orElseThrow(() ->new UserIdNotFoundException("User with id="+c+"not exist!"));
			PropConfirm propConfirm = new PropConfirm();
			propConfirm.setCoordinator(coordinator);
			proposal.addPropToConfirmList(propConfirm);
		}
		
		propRepo.save(proposal);
		
//		System.out.println(body.gpa+"\n"+body.gtu+"\n"+body.cbn);
//		System.out.println(body.description);
//		body.confirmList.stream().forEach(x ->System.out.println(x));
	}

	
	
	
}
