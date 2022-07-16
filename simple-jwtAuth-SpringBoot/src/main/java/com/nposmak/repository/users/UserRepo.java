package com.nposmak.repository.users;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.users.User;

public interface UserRepo extends CrudRepository<User, Long> {
	Optional<User> findByEmail(String email);
	Boolean existsByEmail(String email);
	List<User> findAll();
}
