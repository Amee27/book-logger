package com.amee.booklogger.validators;

import com.amee.booklogger.models.forms.Book;
import com.amee.booklogger.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {

    @Autowired
    private BookService bookService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty", "Title cannot be empty.");
        if (!book.getTitle().isEmpty() && (book.getTitle().length() < 2 || book.getTitle().length() > 150)) {
            errors.rejectValue("title", "Size.book.title", "Title can be 4 to 50 characters long.");
        }

        Book existingBook = bookService.findByTitle(book.getTitle(), book.getUser());
        if (existingBook != null && (book.getId() == null || !existingBook.getId().equals(book.getId()))) {
            errors.rejectValue("title", "NotValid","Book is already exists.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "NotEmpty", "Author cannot be empty.");
        if (!book.getAuthor().isEmpty() && (book.getAuthor().length() < 3 || book.getAuthor().length() > 150)) {
            errors.rejectValue("password", "Size", "Author can be 2 to 150 characters long");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "published_year", "NotEmpty", "Published year cannot be empty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "NotEmpty", "Category cannot be empty");
        if (!book.getCategory().isEmpty() && (book.getCategory().length() < 3 || book.getCategory().length() > 50)) {
            errors.rejectValue("category", "NotValid", "Category can be 2 to 50 characters long");
        }
    }
    
}
