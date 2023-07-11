package com.sim.tracking.service.impl;

import com.sim.tracking.db.dao.IUserDAO;
import com.sim.tracking.db.entity.User;
import com.sim.tracking.model.request.LoginRequest;
import com.sim.tracking.model.request.SignUpRequest;
import com.sim.tracking.model.response.LoginResponse;
import com.sim.tracking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {

        if(request == null){
            throw new Exception("Login request is mandatory");
        }

        LoginResponse response = new LoginResponse();
        //String passwordHash = DigestUtils.sha1Hex(request.getPassword());
        String passwordHash = request.getPassword();

        User userDetails = userDAO.getByUserNamePassword(request.getUserName(), passwordHash);

        if(userDetails == null){
            throw new Exception("Invalid UserName/Password.");
        }

        return response;

    }

    @Override
    public void signUp(SignUpRequest request) throws Exception {


        if(request == null){
            throw new Exception("Request is mandatory");
        }
        if(request.getFirstName() == null || request.getFirstName().trim().length() == 0){
            throw new Exception("FirstName is mandatory");
        }

        if(request.getUserName() == null || request.getUserName().trim().length() == 0){
            throw new Exception("UserName is mandatory");
        }
        if(request.getUserName().trim().length() < 5){
            throw new Exception("Minimum username length is 5");
        }

        if(request.getPassword() == null || request.getPassword().trim().length() == 0){
            throw new Exception("Password is mandatory");
        }
        if(request.getPassword().trim().length() < 5){
            throw new Exception("Minimum password length is 5");
        }
        if(request.getMobile() == null || request.getMobile().trim().length() == 0){
            throw new Exception("Mobile number is mandatory");
        }

        Pattern mobilePattern = Pattern.compile("^\\d{10}$");
        Matcher mobileMatcher = mobilePattern.matcher(request.getMobile());
        if(!mobileMatcher.matches()){
            throw new Exception("Only 10 digit number is allowed");
        }

        if(request.getEmailId() == null || request.getEmailId().trim().length() == 0){
            throw new Exception("EmailId is mandatory");
        }

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getEmailId());
        if(!matcher.matches()){
            throw new Exception("Invalid emailId format");
        }

        User newUser =  new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setUsername(request.getUserName());
        newUser.setPassword(request.getPassword());
        newUser.setMobile(request.getMobile());
        newUser.setEmailId(request.getEmailId());
        userDAO.signUp(newUser);

    }
}
