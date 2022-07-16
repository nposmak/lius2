package com.nposmak.repository.users;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.users.Role;

public interface RoleRepo extends CrudRepository<Role, Long> {
	List<Role> findAll();
	Role findByRoleName(String roleName);

}
