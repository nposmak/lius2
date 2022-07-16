package com.nposmak.repository.users;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.users.Office;

public interface OfficeRepo extends CrudRepository<Office, Long> {
	List<Office> findAll();
	Office findByOffName(String offName);

}
