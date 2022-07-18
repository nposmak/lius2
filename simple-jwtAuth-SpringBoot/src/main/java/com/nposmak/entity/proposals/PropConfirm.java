package com.nposmak.entity.proposals;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.nposmak.entity.users.User;

import lombok.Data;

@Table(name="propconfirm")
@Entity
//@Data
public class PropConfirm {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "proposal_id")
	private Proposal proposal;
	
	@Column(name="confirm_date")
	private Date confDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "coordinator_id")
	private User coordinator;
	
	@Column(name="message")
	private String message;
	
	@Column(name = "desicion")
	private Boolean desicion;

	public PropConfirm() {
	}
	
	public PropConfirm(Long id, Proposal proposal, Date confDate, User coordinator, String message, Boolean desicion) {
		this.id = id;
		this.proposal = proposal;
		this.confDate = confDate;
		this.coordinator = coordinator;
		this.message = message;
		this.desicion = desicion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public Date getConfDate() {
		return confDate;
	}

	public void setConfDate(Date confDate) {
		this.confDate = confDate;
	}

	public User getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(User coordinator) {
		this.coordinator = coordinator;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getDesicion() {
		return desicion;
	}

	public void setDesicion(Boolean desicion) {
		this.desicion = desicion;
	}
	
	
	

	

	
	
	

}
