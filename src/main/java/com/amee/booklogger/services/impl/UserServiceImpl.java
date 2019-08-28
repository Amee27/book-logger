package com.amee.booklogger.services.impl;

import com.amee.booklogger.models.data.UserDao;
import com.amee.booklogger.models.forms.User;
import com.amee.booklogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder = bCryptPasswordEncoder();


    @Override
    public Long save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User newUser = userDao.save(user);
        return newUser.getId();
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}
