package com.amee.booklogger.models.data;

import com.amee.booklogger.models.forms.Book;
import com.amee.booklogger.models.forms.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface BookDao extends CrudRepository<Book, Long> {
    Book findByTitleAndUser(String title, User user);

    List<Book> findAllByUser(User user);
}
