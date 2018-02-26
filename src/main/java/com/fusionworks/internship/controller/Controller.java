package com.fusionworks.internship.controller;

import com.fusionworks.internship.data.User;
import com.fusionworks.internship.service.UserService;
import com.fusionworks.internship.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.Service;


/**
 * Controller for {@link com.fusionworks.internship.data.User}'s pages.
 *
 * @author lapteacru Dumitru
 * @version 1.0
 */


@EnableWebSecurity
@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("loginForm_", new User());
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm_") User user, BindingResult bindingResult){

        if(userService.checkUser(user)){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(),user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return "redirect:welcome";
        } else {
            validator.validate(user,bindingResult);
            if(bindingResult.hasErrors()){
                return "login";
            }
        }

        return "login";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model){
        model.addAttribute("newUserForm", new User());
        return "registration";
    }
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("newUserForm") User newUserForm, BindingResult bindingResult){
        validator.validate(newUserForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "registration";
        }
        userService.saveUser(newUserForm);

        return "redirect:login";
    }

    @RequestMapping(value =  "/welcome", method = RequestMethod.GET)
    public String welcome(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("email", auth.getName());
        return "welcome";
    }
}
