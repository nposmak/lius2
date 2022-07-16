package com.nposmak.repository.proposals;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.Cbn;

public interface CbnRepo extends CrudRepository<Cbn, Long> {
	List<Cbn> findAll();
	Cbn findByCbnName(String cbnName);

}
