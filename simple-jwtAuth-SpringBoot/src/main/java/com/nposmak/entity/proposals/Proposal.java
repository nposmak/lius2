package com.nposmak.entity.proposals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nposmak.entity.users.User;

import lombok.Data;

@Table(name="proposal")
@Entity
@Data
public class Proposal {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	private User author;
	
	@Column(name="propDate")
	private Date propDate;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "gpa_id")
	private Gpa gpa;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "gtuid")
	private Gtu gtu;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "cbn_id")
	private Cbn cbn;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name = "status_id")
	private PropStatus status;
	
	@OneToMany(cascade = CascadeType.ALL,  mappedBy = "proposal")
	private List<PropConfirm> confirmList;
	
//	private List<PropComment> commentList;
//	
//	private List <PropDesicion> desicionList;
	
	
	public void addPropToConfirmList (PropConfirm propConfirm) {
		if(confirmList == null) {
			confirmList = new ArrayList<>();
		}
		confirmList.add(propConfirm);
		propConfirm.setProposal(this);
		
	}

}
