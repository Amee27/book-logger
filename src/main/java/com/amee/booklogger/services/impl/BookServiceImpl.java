package com.amee.booklogger.services.impl;

import com.amee.booklogger.models.data.BookDao;
import com.amee.booklogger.models.forms.Book;
import com.amee.booklogger.models.forms.User;
import com.amee.booklogger.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public Long save(Book newBook) {

        Book book = bookDao.save(newBook);
        return book.getId();
    }

    @Override
    public Book findByTitle(String title) {
        return bookDao.findByTitle(title);
    }

    @Override
    public List<Book> findByUser(User user) {
        return bookDao.findAllByUser(user);
    }
}
