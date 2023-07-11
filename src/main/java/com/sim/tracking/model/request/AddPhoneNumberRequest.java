package com.sim.tracking.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddPhoneNumberRequest {

    private String phone_number;
    private String name;
    private String operator;
}
