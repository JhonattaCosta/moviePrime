package com.moviesPrime.service;

import com.moviesPrime.entity.User;
import com.moviesPrime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }


}
