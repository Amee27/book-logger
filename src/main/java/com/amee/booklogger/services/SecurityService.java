package com.amee.booklogger.services;

public interface SecurityService {
    void login(String username, String password);
    void logout();
}