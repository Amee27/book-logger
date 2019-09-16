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
    public Book findByTitle(String title, User user) {
        return bookDao.findByTitleAndUser(title, user);
    }

    @Override
    public List<Book> findByUser(User user) {
        return bookDao.findAllByUser(user);
    }

    @Override
    public Book findById(Long id) {
        return bookDao.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        bookDao.delete(id);
    }
}
