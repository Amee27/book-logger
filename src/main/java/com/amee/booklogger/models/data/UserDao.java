package com.amee.booklogger.models.data;

import com.amee.booklogger.models.forms.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository // Create a concrete class that implements this interface.
@Transactional // All of the methods within this database should be wrapped by a database transaction.
public interface UserDao extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
