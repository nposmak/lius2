package com.nposmak.repository.users;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.nposmak.entity.users.Position;

public interface PositRepo extends CrudRepository<Position, Long>{
	List<Position> findAll();
	Position findByPosName(String name);
}
