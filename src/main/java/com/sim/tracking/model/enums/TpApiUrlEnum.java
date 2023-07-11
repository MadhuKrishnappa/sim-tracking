package com.sim.tracking.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum TpApiUrlEnum {

    ADD_PHONE_NUMBER("https://dashboard.traqo.in/api/v2/add_phone_number/"),
    CHECK_CONSENT("https://dashboard.traqo.in/api/v2/check_consent/"),
    RESEND_CONSENT_SMS("https://dashboard.traqo.in/api/v2/resend_consent_sms/"),
    FETCH_NUMBER_LIST("https://dashboard.traqo.in/api/v2/number_list/");


    String url;

    public String getUrl() {
        return url;
    }

    private TpApiUrlEnum(String url){
        this.url = url;
    }
}
