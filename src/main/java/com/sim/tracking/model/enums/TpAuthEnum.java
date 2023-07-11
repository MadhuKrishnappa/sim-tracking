package com.sim.tracking.model.enums;

public enum TpAuthEnum {

    USERNAME("9654245923"),
    PASSWORD("9654245923@123");

    String code;

    public String getCode(){
        return code;
    }
    private TpAuthEnum(String code){
        this.code = code;
    }
}
