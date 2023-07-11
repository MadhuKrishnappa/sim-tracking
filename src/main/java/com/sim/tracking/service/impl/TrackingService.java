package com.sim.tracking.service.impl;

import com.google.gson.Gson;
import com.sim.tracking.model.enums.ThirdPartyEventsType;
import com.sim.tracking.model.request.AddPhoneNumberRequest;
import com.sim.tracking.model.request.CheckConsentRequest;
import com.sim.tracking.model.response.AddPhoneNumberResponse;
import com.sim.tracking.model.response.CheckConsentResponse;
import com.sim.tracking.model.response.FetchNumberList;
import com.sim.tracking.model.response.FetchNumberListResponse;
import com.sim.tracking.service.ITrackingService;
import com.sim.tracking.utilities.GenericThirdPartyApiCallingComponent;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TrackingService implements ITrackingService {

    @Autowired
    GenericThirdPartyApiCallingComponent genericThirdPartyApiCallingComponent;

    @Override
    public AddPhoneNumberResponse addPhoneNumber(AddPhoneNumberRequest request) throws Exception {


        if(request == null){
            throw new Exception("Request is mandatory");
        }

        if(request.getPhone_number() == null || request.getPhone_number().trim().length() == 0){
            throw new Exception("Phone number is mandatory");
        }

        Pattern mobilePattern = Pattern.compile("^\\d{10}$");
        Matcher mobileMatcher = mobilePattern.matcher(request.getPhone_number());
        if(!mobileMatcher.matches()){
            throw new Exception("Only 10 digit phone number is allowed");
        }

        if(request.getName() == null || request.getName().trim().length() == 0){
            throw new Exception("Name is mandatory");
        }


        String tpResponse = genericThirdPartyApiCallingComponent.
                callTpApi(new Gson().toJson(request), ThirdPartyEventsType.ADD_PHONE_NUMBER);
        AddPhoneNumberResponse res = new Gson().fromJson(tpResponse, AddPhoneNumberResponse.class);

        return res;

    }

    @Override
    public CheckConsentResponse checkConsent(CheckConsentRequest request) throws Exception {

        if(request == null){
            throw new Exception("Request is mandatory");
        }

        if(request.getTel() == null){
            throw new Exception("Tel is mandatory");
        }

        Pattern mobilePattern = Pattern.compile("^\\d{10}$");
        Matcher mobileMatcher = mobilePattern.matcher(request.getTel());
        if(!mobileMatcher.matches()){
            throw new Exception("Only 10 digit number is allowed");
        }

        String tpResponse = genericThirdPartyApiCallingComponent.
                callTpApi(new Gson().toJson(request), ThirdPartyEventsType.CHECK_CONSENT);
        CheckConsentResponse res = new Gson().fromJson(tpResponse, CheckConsentResponse.class);

        return res;
    }

    @Override
    public AddPhoneNumberResponse resendConsentSms(AddPhoneNumberRequest request) throws Exception {

        if(request == null){
            throw new Exception("Request is mandatory");
        }

        if(request.getPhone_number() == null || request.getPhone_number().trim().length() == 0){
            throw new Exception("Phone number is mandatory");
        }

        Pattern mobilePattern = Pattern.compile("^\\d{10}$");
        Matcher mobileMatcher = mobilePattern.matcher(request.getPhone_number());
        if(!mobileMatcher.matches()){
            throw new Exception("Only 10 digit phone number is allowed");
        }



        String tpResponse = genericThirdPartyApiCallingComponent.
                callTpApi(new Gson().toJson(request), ThirdPartyEventsType.RESEND_CONSENT_SMS);
        AddPhoneNumberResponse res = new Gson().fromJson(tpResponse, AddPhoneNumberResponse.class);

        return res;
    }

    @Override
    public FetchNumberListResponse getNumberList() throws Exception {

        String tpResponse = genericThirdPartyApiCallingComponent.
                callTpApi(null, ThirdPartyEventsType.NUMBER_LIST);
        FetchNumberListResponse fetchNumberListResponse = new FetchNumberListResponse();
        List<FetchNumberList> fetchNumberLists = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(tpResponse);
        if(json != null && json.size() > 0){
            for(int i = json.size() - 1; i >= 0; i--){

                FetchNumberList numberList = new Gson().fromJson(json.get(String.valueOf(i)).toString(), FetchNumberList.class);
                JSONObject jo = (JSONObject) json.get(String.valueOf(i));
                String phoneNumberString = jo.get("phone_number").toString();
                long phoneNumber = Double.valueOf(phoneNumberString).longValue();
                numberList.setPhone_number(String.valueOf(phoneNumber));
                fetchNumberLists.add(numberList);
            }
            fetchNumberListResponse.setNumberLists(fetchNumberLists);
        }
        return fetchNumberListResponse;
    }
}
