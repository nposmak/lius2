package com.nposmak.pojo;

import java.util.Date;
import java.util.List;

import com.nposmak.entity.proposals.Cbn;
import com.nposmak.entity.proposals.Gpa;
import com.nposmak.entity.proposals.Gtu;
import com.nposmak.entity.proposals.PropConfirm;
import com.nposmak.entity.proposals.PropStatus;
import com.nposmak.entity.users.User;

import lombok.Data;

@Data
public class CreatePropResponse {
	
	private Long id;
	private User author;
	private Date propDate;
	private Gpa gpa;
	private Gtu gtu;
	private Cbn cbn;
	private String description;
	private PropStatus status;
	private List<PropConfirm> confirmList;

}
