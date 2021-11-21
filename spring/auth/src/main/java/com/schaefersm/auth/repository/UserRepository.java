package com.schaefersm.auth.repository;

import com.schaefersm.auth.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

    User findByName(String name);

    User findByEmail(String email);

}
