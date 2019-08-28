package com.amee.booklogger.controllers;

import com.amee.booklogger.models.forms.User;
import com.amee.booklogger.services.BookService;
import com.amee.booklogger.services.SecurityService;
import com.amee.booklogger.services.UserService;
import com.amee.booklogger.validators.UserValidator;
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
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "redirect:/book-logger/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("title", "Welcome To Book Logger!");
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String authenticate(HttpSession session, @ModelAttribute("userForm") User userForm,
                               BindingResult bindingResult) {

        userValidator.validateLoginInfo(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/login";
        }


        securityService.login(userForm.getEmail(), userForm.getPassword());
        session.setAttribute("userName", userForm.getEmail());

        return "redirect:/book-logger/home";
    }

    @RequestMapping(value = "register")
    public String register(Model model) {
        model.addAttribute("registerForm", new User());
        return "/register";
    }

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public String registration(HttpSession session, @ModelAttribute("registerForm") User userForm,
                               BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/register";
        }
        String password = userForm.getPassword();
        userForm.setActive(Boolean.TRUE);
        userService.save(userForm);
        securityService.login(userForm.getEmail(), password);
        session.setAttribute("userName", userForm.getEmail());

        return "redirect:/book-logger/home";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String homePage(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("userName");
        if (userName == null) {
            return "redirect:/book-logger/error";
        }
        User user = userService.findByEmail(userName);
        model.addAttribute("title", "Welcome " + user.getName() + "!");
        model.addAttribute("userName", userName);
        model.addAttribute("books", bookService.findByUser(user));
        return "home";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session, Model model) {
        securityService.logout();
        session.removeAttribute("userName");
        return "redirect:/book-logger/login";
    }

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        model.addAttribute("title", "Oops!");
        return "/error";
    }
}
