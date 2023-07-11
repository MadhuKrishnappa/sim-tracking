package com.sim.tracking.controller;


import com.sim.tracking.model.ResponseStatus;
import com.sim.tracking.model.ServiceResponse;
import com.sim.tracking.model.request.AddPhoneNumberRequest;
import com.sim.tracking.model.request.CheckConsentRequest;
import com.sim.tracking.model.request.LoginRequest;
import com.sim.tracking.model.response.AddPhoneNumberResponse;
import com.sim.tracking.model.response.CheckConsentResponse;
import com.sim.tracking.model.response.FetchNumberListResponse;
import com.sim.tracking.model.response.LoginResponse;
import com.sim.tracking.service.ITrackingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TrackingController {


    @Autowired
    ITrackingService trackingService;

    @RequestMapping(path = "/addPhoneNumber", method = RequestMethod.POST)
    public ServiceResponse<AddPhoneNumberResponse> addPhoneNumber(HttpServletRequest httpRequest,
                                                @RequestBody AddPhoneNumberRequest request) throws Exception {


        ServiceResponse<AddPhoneNumberResponse> response = new ServiceResponse<>();
        ResponseStatus status = new ResponseStatus();
        try {
            response.setResponse(trackingService.addPhoneNumber(request));
            status.setCode(HttpStatus.OK.value());
            status.setMessage("Successful Created");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }

    @RequestMapping(path = "/checkConsent", method = RequestMethod.POST)
    public ServiceResponse<CheckConsentResponse> checkConsent(HttpServletRequest httpRequest,
                                                         @RequestBody CheckConsentRequest request) throws Exception {


        ServiceResponse<CheckConsentResponse> response = new ServiceResponse<>();
        ResponseStatus status = new ResponseStatus();
        try {
            response.setResponse(trackingService.checkConsent(request));
            status.setCode(HttpStatus.OK.value());
            status.setMessage("Success");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }

    @RequestMapping(path = "/resendConsentSms", method = RequestMethod.POST)
    public ServiceResponse<AddPhoneNumberResponse> resendConsentSms(HttpServletRequest httpRequest,
                                                              @RequestBody AddPhoneNumberRequest request) throws Exception {


        ServiceResponse<AddPhoneNumberResponse> response = new ServiceResponse<>();
        ResponseStatus status = new ResponseStatus();
        try {
            response.setResponse(trackingService.resendConsentSms(request));
            status.setCode(HttpStatus.OK.value());
            status.setMessage("Success");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }

    @RequestMapping(path = "/numberList", method = RequestMethod.GET)
    public ServiceResponse<FetchNumberListResponse> getNumberList(HttpServletRequest httpRequest) throws Exception {


        ServiceResponse<FetchNumberListResponse> response = new ServiceResponse<>();
        ResponseStatus status = new ResponseStatus();
        try {
            response.setResponse(trackingService.getNumberList());
            status.setCode(HttpStatus.OK.value());
            status.setMessage("Success");
        }catch (Exception e){
            e.printStackTrace();
            status.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            status.setMessage(e.getMessage());
        }
        response.setStatus(status);

        return response;
    }



}
