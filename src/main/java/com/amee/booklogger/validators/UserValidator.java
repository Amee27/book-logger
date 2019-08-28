package com.amee.booklogger.validators;

import com.amee.booklogger.models.forms.User;
import com.amee.booklogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty", "Name cannot be empty.");
        if (!user.getName().isEmpty() && (user.getName().length() < 4 || user.getName().length() > 50)) {
            errors.rejectValue("name", "Size.userForm.name", "Name can be 4 to 50 characters long.");
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "NotValid","Email Id is already exists.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "Password cannot be empty.");
        if (!user.getPassword().isEmpty() && (user.getPassword().length() < 4 || user.getPassword().length() > 32)) {
            errors.rejectValue("password", "Size", "Password can be 4 to 32 characters long");
        }

        if (!user.getPassword().equals(user.getVerifyPassword())) {
            errors.rejectValue("verifyPassword", "NotValid","Password does not match");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty", "Email cannot be empty");
        if (!user.getEmail().isEmpty() && (user.getEmail().length() < 5 || user.getEmail().length() > 30)) {
            errors.rejectValue("email", "NotValid", "Email can be 6 to 30 characters long");
        }

    }

    public void validateLoginInfo(User userForm, BindingResult bindingResult) {

        User user = userService.findByEmail(userForm.getEmail());
        if (user != null && !userForm.getPassword().isEmpty()) {
            if (!bCryptPasswordEncoder.matches(userForm.getPassword(), user.getPassword())){
                bindingResult.rejectValue("email", "NotValid", "Please enter valid email or password.");
            }
        } else {
            bindingResult.rejectValue("email", "NotValid", "Please enter valid email or password.");
        }
    }
}
