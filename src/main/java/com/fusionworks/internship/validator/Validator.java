package com.fusionworks.internship.validator;

import com.fusionworks.internship.data.User;
import com.fusionworks.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Validator for {@link com.fusionworks.internship.data.User} class.
 *
 * @author Lapteacru Dumitru
 * @version 1.0
 */

@Component
public class Validator implements org.springframework.validation.Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if(user.getPassword().length() < 4 || user.getPassword().length() > 20 || !checkPassword(user.getPassword())){
            errors.rejectValue("password", "Wrong.newUserForm.password","Password must be between 4 and 20 characters containing only letters, numbers and ‘_’.");
        }
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password", "Different.passwords","Passwords does not match.");
        }

        if(userService.findUserByEmail(user.getEmail())!=null){
            errors.rejectValue("email", "Duplicate.email","Email already registered.");
        }

        if(userService.findUserByEmail(user.getEmail())!=null){
            errors.rejectValue("state", "Wrong.LogIn", "Password invalid, please verify.");
        } else errors.rejectValue("state", "Wrong.LogIn","Email invalid, please verify.");

    }
    private boolean checkPassword(String password){
        for (int index=0; index < password.length(); index++){
            if(!Character.isLetter(password.charAt(index))){
                if(password.charAt(index)!=95){
                    return false;
                }
            }
        }
        return true;
    }
}
