package com.nposmak.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.nposmak.exceptions.ProposalIdNotFoundException;
import com.nposmak.exceptions.UserEmailNotFoundException;
import com.nposmak.repository.proposals.PropConfirmRepo;
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
	private PropConfirmRepo confirmRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	record MyPropResponse(Long id, String authorFullName, String date, String gpa, String gtu, String cbn, String description, String status, String confList) {}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myprops")
	public List<MyPropResponse> getProps(@RequestHeader("Authorization") String tokenHeader) throws Exception {
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		
		List<Proposal> propList = propRepo.findAllByAuthor(user);
		List<MyPropResponse> response = new ArrayList<MyPropResponse>();
		
		String pattern = " dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		propList.stream().forEach(prop->response.add(new MyPropResponse(
				prop.getId(),
				prop.getAuthor().fullNameShort(),
				simpleDateFormat.format(prop.getPropDate()),
				prop.getGpa().getGpaName(),
				prop.getGtu().getGtuName(),
				prop.getCbn().getCbnName(),
				prop.getDescription(),
				prop.getStatus().getStatus(),
				prop.getConfListUsersDesicions()
				)));

		return response;
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myprops/toconfirm")
	public List<MyPropResponse> getPropsToConfirm (@RequestHeader("Authorization") String tokenHeader) throws Exception{
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		
		List<PropConfirm> confirmList = confirmRepo.findByCoordinator(user);
		List<Proposal> propList = new ArrayList<>();
		confirmList.stream().forEach(c->propList.add(c.getProposal()));
		
		String pattern = " dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		List<MyPropResponse> response = new ArrayList<MyPropResponse>();
		propList.stream().forEach(prop->response.add(new MyPropResponse(
				prop.getId(),
				prop.getAuthor().fullNameShort(),
				simpleDateFormat.format(prop.getPropDate()),
				prop.getGpa().getGpaName(),
				prop.getGtu().getGtuName(),
				prop.getCbn().getCbnName(),
				prop.getDescription(),
				prop.getStatus().getStatus(),
				prop.getConfListUsersDesicions()
				)));
		return response;
	}

	
	record PropToConfirmResponse(Long id, String author, String date, String gpa, String gtu, String cbn,
			String description, String status, List<PropConfirm> confirmList) {}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myprops/toconfirm/{propId}")
	public PropToConfirmResponse getPropToConfirmInfo(@RequestHeader("Authorization") String tokenHeader,
			@PathVariable ("propId") String propId) throws Exception {
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		Proposal prop = propRepo.findById(Long.valueOf(propId)).orElseThrow(() -> new ProposalIdNotFoundException("Proposal with id = "+ propId+"not exist!"));
		
		/**Очень тупое скрытие роута, если пользователя нет в списке согласования, то страница будет пустая*/
		List<PropConfirm> cList = prop.getConfirmList();
		List<User> coordinators = new ArrayList<>();
		cList.stream().forEach(c->coordinators.add(c.getCoordinator()));
		if(!coordinators.contains(user)) {
			System.out.println("NOT COORDINATOR");
			return null;
		}
		System.out.println("COORDINATOR");
		
		String pattern = " dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		return new PropToConfirmResponse(
				prop.getId(),
				prop.getAuthor().fullNameShortAndOffice(),
				simpleDateFormat.format(prop.getPropDate()),
				prop.getGpa().getGpaName(),
				prop.getGtu().getGtuName(),
				prop.getCbn().getCbnName(),
				prop.getDescription(),
				prop.getStatus().getStatus(),
				prop.getConfirmList()
				);
	}
	
	record AcceptRequest(String confirmMess) {}
	record AcceptResponse(String message) {}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/myprops/toconfirm/{propId}/accept")
	public AcceptResponse confirmProp(@RequestHeader("Authorization") String tokenHeader,
			@PathVariable ("propId") String propId, @RequestBody AcceptRequest req) throws Exception {
		
		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		Proposal prop = propRepo.findById(Long.valueOf(propId)).orElseThrow(() -> new ProposalIdNotFoundException("Proposal with id = "+ propId+"not exist!"));
		PropConfirm confirmProp = confirmRepo.findByProposalAndCoordinator(prop, user);
		
		Date date = new Date();
		
		confirmProp.setDesicion(true);
		confirmProp.setConfDate(date);
		confirmProp.setMessage((req.confirmMess != null) ? req.confirmMess : "Согласовано.");
		
		confirmRepo.save(confirmProp);
		
		return new AcceptResponse("предложение согласованно пользователем " + user.fullNameShort());
	}
	
	record RejectRequest(String rejectMess) {}
	record RejectResponse(String message) {}
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/myprops/toconfirm/{propId}/reject")
	public RejectResponse rejectPorp(@RequestHeader("Authorization") String tokenHeader,
			@PathVariable("propId") String propId, @RequestBody RejectRequest req) throws Exception {

		String token = tokenHeader.substring(7);
		String email = jwtUtils.getUserEmailFromJwtToken(token);
		User user = userRepo.findByEmail(email).orElseThrow(()-> new UserEmailNotFoundException("User with email = "+email+" not exist!"));
		Proposal prop = propRepo.findById(Long.valueOf(propId)).orElseThrow(() -> new ProposalIdNotFoundException("Proposal with id = "+ propId+"not exist!"));
		PropConfirm confirmProp = confirmRepo.findByProposalAndCoordinator(prop, user);
		
		Date date = new Date();
		
		confirmProp.setDesicion(false);
		confirmProp.setConfDate(date);
		confirmProp.setMessage((req.rejectMess != null) ? req.rejectMess : "Отклонено.");
		
		confirmRepo.save(confirmProp);
		
		return new RejectResponse("предложение отклонено пользователем "+user.fullNameShort());
	}
	
}






