package com.nposmak.repository.proposals;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.Proposal;

public interface ProposalRepo extends CrudRepository<Proposal, Long>{

}
