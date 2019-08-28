package com.amee.booklogger.services;

import com.amee.booklogger.models.forms.User;

public interface UserService {

    Long save(User user);
    User findByEmail(String email);
}
