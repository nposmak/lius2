package com.nposmak.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nposmak.entity.users.Department;
import com.nposmak.entity.users.Office;
import com.nposmak.entity.users.Position;
import com.nposmak.entity.users.Role;
import com.nposmak.entity.users.User;
import com.nposmak.repository.users.DepartRepo;
import com.nposmak.repository.users.OfficeRepo;
import com.nposmak.repository.users.PositRepo;
import com.nposmak.repository.users.RoleRepo;
import com.nposmak.repository.users.UserRepo;

@RestController
@RequestMapping("/get-all")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GetFieldsForUserSignUp {
	
	@Autowired
	private DepartRepo departRepo;
	
	@Autowired
	private PositRepo positRepo;
	
	@Autowired
	private OfficeRepo officeRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	record getOffResponse(Long id, String offName) {}
	@GetMapping("/office")
	public List<getOffResponse> getOffices(){
		List<Office> offList = officeRepo.findAll();
		List<getOffResponse> response = new ArrayList<>();
		for(Office o : offList) {
			response.add(new getOffResponse(o.getId(), o.getOffName()));
		}
		return response;
	}

	record getDepResponse(Long id, String depName) {}
	@GetMapping("/department")
	public List<getDepResponse> getDepartments(){
		List<Department> depList = departRepo.findAll();
		List <getDepResponse> response = new ArrayList<>();
		for(Department dep : depList) {
			response.add(new getDepResponse(dep.getId(), dep.getDepName()));
		}
		return response;
	}
	
	record getPosResponse(Long id, String posName) {}
	@GetMapping("/position")
	public List<getPosResponse> getPositions(){
		List<Position> posList = positRepo.findAll();
		List<getPosResponse> response= new ArrayList<>();
		for(Position pos : posList) {
			response.add(new getPosResponse(pos.getId(), pos.getPosName()));
		}
		return response;
	}
	
	record getRoleResponse(Long id, String roleName) {}
	@GetMapping("/role")
	public List<getRoleResponse> getRoles(){
		List<Role> roList = roleRepo.findAll();
		List<getRoleResponse> response = new ArrayList<>();
		for(Role r : roList) {
			response.add(new getRoleResponse(r.getId(), r.getRoleName()));
		}
		return response;
	}


//	record allUsersResponse(String userName) {}
//	@GetMapping("/users")
//	public List<allUsersResponse> getUsers(){
//		List<User> usrList =  userRepo.findAll();
//		List<allUsersResponse> response = new ArrayList<>();
//		for(User user : usrList) {
//			response.add(new allUsersResponse(user.getName() +" "+user.getSname()));
//		}
//		return response;
//	}
	
}



