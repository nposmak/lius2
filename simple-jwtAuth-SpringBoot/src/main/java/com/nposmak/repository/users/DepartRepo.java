package com.nposmak.repository.users;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.users.Department;

public interface DepartRepo extends CrudRepository<Department, Long> {
	List<Department>findAll();
	Department findByDepName(String name);

}
