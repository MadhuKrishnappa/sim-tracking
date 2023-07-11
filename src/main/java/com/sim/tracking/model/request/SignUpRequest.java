package com.sim.tracking.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String mobile;
    private String userName;
    private String password;
    private String emailId;

}
