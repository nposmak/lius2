package com.nposmak.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nposmak.entity.proposals.Cbn;
import com.nposmak.entity.proposals.Gpa;
import com.nposmak.entity.proposals.Gtu;
import com.nposmak.entity.users.User;
import com.nposmak.repository.proposals.CbnRepo;
import com.nposmak.repository.proposals.GpaRepo;
import com.nposmak.repository.proposals.GtuRepo;
import com.nposmak.repository.users.UserRepo;

@RestController
@RequestMapping("/get-all")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GetFieldsForPropCreate {
	
	@Autowired
	private GpaRepo gpaRepo;
	
	@Autowired
	private GtuRepo gtuRepo;
	
	@Autowired
	private  CbnRepo cbnRepo;
	
	@Autowired
	private UserRepo userRepo;

	record getGpaResponse(Long id, String gpaname) {}
	@GetMapping("/gpa")
	public List<getGpaResponse> getGpas(){
		List<Gpa> gpaList = gpaRepo.findAll();
		List<getGpaResponse> response = new ArrayList<>();
		for(Gpa g : gpaList) {
			response.add(new getGpaResponse(g.getId(), g.getGpaName()));
		}
		return response;
	}

	record getGtuResponse(Long id, String gtuname) {}
	@GetMapping("/gtu")
	public List<getGtuResponse> getGtus(){
		List<Gtu> gtuList = gtuRepo.findAll();
		List <getGtuResponse> response = new ArrayList<>();
		for (Gtu g : gtuList) {
			response.add(new getGtuResponse(g.getId(), g.getGtuName()));
		}
		return response;
	}
	
	record getCbnResponse(Long id, String cbnname) {}
	@GetMapping("/cbn")
	public List<getCbnResponse> getCbns(){
		List <Cbn> cbnList = cbnRepo.findAll();
		List<getCbnResponse> response = new ArrayList<>();
		cbnList.stream().forEach(c->response.add(new getCbnResponse(c.getId(), c.getCbnName())));
		return response;
	}
	
	record allUsersResponse(Long id, String name, String sname, String fname, String office, String department, String position) {}
	@GetMapping("/users")
	public List<allUsersResponse> getUsers(){
		List<User> userList = userRepo.findAll();
		List<allUsersResponse> response = new ArrayList<>();
		userList.stream().forEach(u -> response.add(new allUsersResponse
				(u.getId(), u.getName(), u.getSname(), u.getFname(), u.getOffice().getOffName(), u.getDepartment().getDepName(), u.getPosition().getPosName())));
		return response;
	}
	
}//end




