package com.schaefersm.chartissimo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.schaefersm.chartissimo.model.UserConfig;

public interface UserConfigRepository extends CrudRepository<UserConfig, String> {

	ResponseEntity<UserConfig> findByUser(ObjectId userId);

}
