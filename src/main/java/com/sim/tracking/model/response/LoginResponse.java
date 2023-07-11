package com.sim.tracking.model.response;

import com.sim.tracking.model.IResponse;
import com.sim.tracking.model.TokenBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
public class LoginResponse implements IResponse {

    private TokenBO tokenBO;
}
