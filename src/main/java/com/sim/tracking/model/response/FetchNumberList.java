package com.sim.tracking.model.response;

import com.sim.tracking.model.IResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
@ToString
public class FetchNumberList implements IResponse {


    private String phone_number;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String location;
    private String update_at;
    private String created_at;
    private String status;
    private String operator;
    private String last_24h;
    private String name;
    private String gender;
    private Integer total_distance;
    private Integer total_requests;
    private BigDecimal avg_speed;
}
