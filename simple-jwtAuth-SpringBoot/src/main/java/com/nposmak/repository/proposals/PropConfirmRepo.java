package com.nposmak.repository.proposals;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.PropConfirm;
import com.nposmak.entity.proposals.Proposal;
import com.nposmak.entity.users.User;

public interface PropConfirmRepo extends CrudRepository<PropConfirm, Long>{
	List<PropConfirm> findByCoordinator (User user);
	PropConfirm findByProposalAndCoordinator(Proposal proposal, User coordinator);

}
