package com.sim.tracking.service;

import com.sim.tracking.model.request.AddPhoneNumberRequest;
import com.sim.tracking.model.request.CheckConsentRequest;
import com.sim.tracking.model.response.AddPhoneNumberResponse;
import com.sim.tracking.model.response.CheckConsentResponse;
import com.sim.tracking.model.response.FetchNumberListResponse;

public interface ITrackingService {
    AddPhoneNumberResponse addPhoneNumber(AddPhoneNumberRequest request) throws Exception;

    CheckConsentResponse checkConsent(CheckConsentRequest request) throws Exception;

    AddPhoneNumberResponse resendConsentSms(AddPhoneNumberRequest request) throws Exception;

    FetchNumberListResponse getNumberList() throws Exception;

}
