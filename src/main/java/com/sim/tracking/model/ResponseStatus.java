package com.sim.tracking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseStatus implements Serializable {


    private static final long serialVersionUID = -2412481864560286535L;
    private Integer code;
    private String message;

    public ResponseStatus() {
        super();
    }

    public ResponseStatus(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public static ResponseStatus getOK(){
        ResponseStatus status = new ResponseStatus();
        status.setCode(HttpStatus.ACCEPTED.value());
        status.setMessage("SUCCESS");
        return status;
    }

    public static ResponseStatus getInterServerError(String message){
        ResponseStatus status = new ResponseStatus();
        status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        status.setMessage(StringUtils.isEmpty(message) ? "Internal Server Error" : message);
        return status;
    }


}
