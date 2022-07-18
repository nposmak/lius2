package com.nposmak.repository.proposals;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.Proposal;
import com.nposmak.entity.users.User;

public interface ProposalRepo extends CrudRepository<Proposal, Long>{
	
	List<Proposal> findAllByAuthor(User user);

}
