package com.sim.tracking.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Setter
@Getter
@ToString
public class HttpHelperResponse<T, J> {

    T response;
    J error;
    Map<String, String> headers;
}
