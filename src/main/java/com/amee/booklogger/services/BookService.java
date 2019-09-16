package com.amee.booklogger.services;

import com.amee.booklogger.models.forms.Book;
import com.amee.booklogger.models.forms.User;

import java.util.List;

public interface BookService {
    Long save(Book book);
    Book findByTitle(String title, User user);
    Book findById(Long id);
    List<Book> findByUser(User user);
    void deleteById(Long id);
}
