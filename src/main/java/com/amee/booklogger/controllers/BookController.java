package com.amee.booklogger.controllers;

import com.amee.booklogger.models.forms.Book;
import com.amee.booklogger.models.forms.User;
import com.amee.booklogger.services.BookService;
import com.amee.booklogger.services.UserService;
import com.amee.booklogger.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("book-logger")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;


    @Autowired
    private BookValidator bookValidator;

    @RequestMapping(value = "add")
    public String addBook(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/book-logger/error";
        }
        model.addAttribute("userName", userName);
        model.addAttribute("book", new Book());
        return "/addBook";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String saveBook(HttpSession session, @ModelAttribute("book") Book book,
                               BindingResult bindingResult) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/book-logger/error";
        }
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/addBook";
        }

        User user = userService.findByEmail(userName);
        book.setUser(user);
        bookService.save(book);

        return "redirect:/book-logger/home";
    }
}
