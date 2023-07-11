package com.sim.tracking.model.response;

import com.sim.tracking.model.IResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AddPhoneNumberResponse implements IResponse {

    private String status;

}
