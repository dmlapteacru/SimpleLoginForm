package com.fusionworks.internship.service;


import com.fusionworks.internship.dao.UserRepository;
import com.fusionworks.internship.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean checkUser(User user){
        User temp = findUserByEmail(user.getEmail());
        if(temp!=null && bCryptPasswordEncoder.matches(user.getPassword(),temp.getPassword())){
            return true;
        } return false;
    }

}
