package com.nposmak.entity.proposals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	
	@OneToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "author_id")
	private User author;
	
	@Column(name="prop_date")
	private Date propDate;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "gpa_id")
	private Gpa gpa;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "gtu_id")
	private Gtu gtu;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "cbn_id")
	private Cbn cbn;
	
	@Column(name="description")
	private String description;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "status_id")
	private PropStatus status;
	
	//@JsonBackReference
	@OneToMany(cascade = CascadeType.ALL,  mappedBy = "proposal")
	private List<PropConfirm> confirmList;
	
	
	public void addPropToConfirmList (PropConfirm propConfirm) {
		if(confirmList == null) {
			confirmList = new ArrayList<>();
		}
		confirmList.add(propConfirm);
		propConfirm.setProposal(this);
		
	}
	
	public String getConfListUsersDesicions() {
		String users ="";
		for (PropConfirm c : this.confirmList) {
			users =users +  c.getCoordinator().getSname()+" "
					+c.getCoordinator().getName().substring(0, 1)+"."
					+c.getCoordinator().getFname().substring(0, 1)+"."+" - ";
			try {
				Optional<Boolean> opt  = Optional.ofNullable(c.getDesicion());
				if(opt.get().equals(true)) {
					users = users + "согласовано"+";   ";
				} else if(opt.get().equals(false)) {
					users = users+"оклонено"+";   ";
				}
			}catch(NoSuchElementException e) {
				System.out.println("I know this is bad desicion...but who cares...");
			}
			users = users+"рассмотрение"+";   ";
		}
		return users;
	}
//GETTERS SETTERS CONSTRUCTOR
	public Proposal(Long id, User author, Date propDate, Gpa gpa, Gtu gtu, Cbn cbn, String description, PropStatus status,
			List<PropConfirm> confirmList) {
		this.id = id;
		this.author = author;
		this.propDate = propDate;
		this.gpa = gpa;
		this.gtu = gtu;
		this.cbn = cbn;
		this.description = description;
		this.status = status;
		this.confirmList = confirmList;
	}
	
	public Proposal() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getPropDate() {
		return propDate;
	}

	public void setPropDate(Date propDate) {
		this.propDate = propDate;
	}

	public Gpa getGpa() {
		return gpa;
	}

	public void setGpa(Gpa gpa) {
		this.gpa = gpa;
	}

	public Gtu getGtu() {
		return gtu;
	}

	public void setGtu(Gtu gtu) {
		this.gtu = gtu;
	}

	public Cbn getCbn() {
		return cbn;
	}

	public void setCbn(Cbn cbn) {
		this.cbn = cbn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PropStatus getStatus() {
		return status;
	}

	public void setStatus(PropStatus status) {
		this.status = status;
	}

	public List<PropConfirm> getConfirmList() {
		return confirmList;
	}

	public void setConfirmList(List<PropConfirm> confirmList) {
		this.confirmList = confirmList;
	}
	
}
