package com.sim.tracking.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@ToString
public class UserBO {

    private BigInteger id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private Boolean status;

    private String mobile;

    private String emailId;

    private BigInteger partnerId;

    private String role;

    private Date createdAt;

    private Date updatedAt;
}
