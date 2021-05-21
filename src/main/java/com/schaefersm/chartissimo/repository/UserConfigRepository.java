package com.schaefersm.chartissimo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.schaefersm.chartissimo.model.UserConfig;

public interface UserConfigRepository extends CrudRepository<UserConfig, String> {
	Optional<UserConfig> findByUserId(String id);
}
