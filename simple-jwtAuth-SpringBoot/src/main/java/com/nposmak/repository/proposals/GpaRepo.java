package com.nposmak.repository.proposals;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.proposals.Gpa;

public interface GpaRepo extends CrudRepository<Gpa, Long>{
	List<Gpa> findAll();
	Gpa findByGpaName(String gpaName);

}
