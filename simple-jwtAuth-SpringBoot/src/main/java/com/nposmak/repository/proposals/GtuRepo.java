package com.nposmak.repository.proposals;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.Gtu;

public interface GtuRepo extends CrudRepository<Gtu, Long> {
	List<Gtu> findAll();
	Gtu findByGtuName(String gtuName);
}
