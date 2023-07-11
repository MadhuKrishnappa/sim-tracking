package com.sim.tracking.service;

import com.sim.tracking.model.request.LoginRequest;
import com.sim.tracking.model.request.SignUpRequest;
import com.sim.tracking.model.response.LoginResponse;

public interface IUserService {
    LoginResponse login(LoginRequest request) throws Exception;

    void signUp(SignUpRequest request) throws Exception;
}
