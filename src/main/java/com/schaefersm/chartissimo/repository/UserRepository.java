package com.schaefersm.chartissimo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.schaefersm.chartissimo.model.User;

public interface UserRepository extends CrudRepository<User, String> {
	Optional<User> findByName(String name);

	@Override
	Optional<User> findById(String id);

}
