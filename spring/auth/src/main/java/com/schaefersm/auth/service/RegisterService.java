package com.schaefersm.auth.service;


import com.schaefersm.auth.exception.EmailAlreadyExistsException;
import com.schaefersm.auth.exception.UsernameAlreadyExistsException;
import com.schaefersm.auth.exception.ValidationException;
import com.schaefersm.auth.model.User;
import com.schaefersm.auth.repository.UserRepository;
import com.schaefersm.auth.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ValidationUtil validationUtil;

    public void register(String name, String password, String email) {
        validationUtil.validateUsername(name);
        validationUtil.validateEmail(email);
        validationUtil.validatePassword(password);
        checkUsername(name);
        checkEmail(email);
        User registeredUser = new User(name, email, password);
        userRepository.save(registeredUser);
    }

    private void checkEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new EmailAlreadyExistsException();
        }
    }

    private void checkUsername(String name) {
        User user = userRepository.findByName(name);
        if (user != null) {
            throw new UsernameAlreadyExistsException();
        }
    }

}
