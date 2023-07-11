package com.sim.tracking.controller;

import com.sim.tracking.model.ResponseStatus;
import com.sim.tracking.model.ServiceResponse;
import com.sim.tracking.model.request.LoginRequest;
import com.sim.tracking.model.request.SignUpRequest;
import com.sim.tracking.model.response.LoginResponse;
import com.sim.tracking.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class SignInController {

    @Autowired
    IUserService userService;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ServiceResponse<LoginResponse> login(HttpServletRequest httpRequest,
                                                @RequestBody LoginRequest request) throws Exception {


        ServiceResponse<LoginResponse> response = new ServiceResponse<>();
        ResponseStatus status = new ResponseStatus();
        try {
            LoginResponse loginResponse = userService.login(request);
            response.setResponse(loginResponse);
            status.setCode(HttpStatus.OK.value());
            status.setMessage("Login Successful");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ServiceResponse login(HttpServletRequest httpRequest,
                                                @RequestBody SignUpRequest request) throws Exception {


        ServiceResponse response = new ServiceResponse();
        ResponseStatus status = new ResponseStatus();
        try {
            userService.signUp(request);
            status.setCode(HttpStatus.OK.value());
            status.setMessage("SignUp Successful");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }


}
