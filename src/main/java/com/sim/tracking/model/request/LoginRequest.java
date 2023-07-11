package com.sim.tracking.model.request;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class LoginRequest implements Serializable {


    private static final long serialVersionUID = -8214712953741537209L;
    private String userName;
    private String password;

}
