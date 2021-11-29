package com.schaefersm.chartissimo.repository;

import org.springframework.data.repository.CrudRepository;
import com.schaefersm.chartissimo.model.UserConfig;

public interface UserConfigRepository extends CrudRepository<UserConfig, String> {
}
