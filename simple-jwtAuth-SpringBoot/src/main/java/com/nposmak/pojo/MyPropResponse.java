//package com.nposmak.pojo;
//
//import java.util.Date;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.nposmak.entity.proposals.Cbn;
//import com.nposmak.entity.proposals.Gpa;
//import com.nposmak.entity.proposals.Gtu;
//import com.nposmak.entity.proposals.PropConfirm;
//import com.nposmak.entity.proposals.PropStatus;
//import com.nposmak.entity.users.User;
//
//public class MyPropResponse {
//
//	@JsonProperty("id")
//	private Long id;
//	@JsonProperty("author")
//	private User author;
//	@JsonProperty("date")
//	private Date propDate;
//	@JsonProperty("gpa")
//	private Gpa gpa;
//	@JsonProperty("gtu")
//	private Gtu gtu;
//	@JsonProperty("cbn")
//	private Cbn cbn;
//	@JsonProperty("description")
//	private String description;
//	@JsonProperty("status")
//	private PropStatus status;
////	@JsonProperty("confirm")
////	private List<PropConfirm> confirmList;
//	
//	public MyPropResponse() {}
//	
//	public MyPropResponse(Long id, User author, Date propDate, Gpa gpa, Gtu gtu, Cbn cbn, String description,
//			PropStatus status) {
//		super();
//		this.id = id;
//		this.author = author;
//		this.propDate = propDate;
//		this.gpa = gpa;
//		this.gtu = gtu;
//		this.cbn = cbn;
//		this.description = description;
//		this.status = status;
//		//this.confirmList = confirmList;
//	}
//	
//	
//}
