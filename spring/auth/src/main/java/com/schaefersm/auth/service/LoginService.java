package com.schaefersm.auth.service;

import com.schaefersm.auth.exception.PasswordIsWrongException;
import com.schaefersm.auth.exception.UserNotFoundException;
import com.schaefersm.auth.exception.ValidationException;
import com.schaefersm.auth.model.AuthResponse;
import com.schaefersm.auth.model.User;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.util.ValidationUtil;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class LoginService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidationUtil validationUtil;

    public User login(String name, String password) {
        validationUtil.validateUsername(name);
        validationUtil.validatePassword(password);
        User user = userRepository.findByName(name);
        log.info("Logging in user " + name + "...");
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.getPassword().equals(password)) {
            throw new PasswordIsWrongException();
        }
        return user;
    }

}
