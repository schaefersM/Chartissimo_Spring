package com.schaefersm.auth.repository;

import com.schaefersm.auth.model.JwtToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JwtRepository extends CrudRepository<JwtToken, String> {


    Optional<JwtToken> findJwtTokenByToken(String token);

}
