package com.sim.tracking.utilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.sim.tracking.helper.JsonHelper;
import com.sim.tracking.model.GenericErrorBO;
import com.sim.tracking.model.IObjectMapperJson;
import com.sim.tracking.model.enums.ThirdPartyEventsType;
import com.sim.tracking.model.enums.TpApiUrlEnum;
import com.sim.tracking.model.enums.TpAuthEnum;
import com.sim.tracking.model.response.HttpHelperResponse;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GenericThirdPartyApiCallingComponent {

    private static final int TIME_OUT = 50;

    private static Logger logger = LoggerFactory.getLogger(GenericThirdPartyApiCallingComponent.class);

    public String callTpApi(String requestJson, ThirdPartyEventsType eventType) throws Exception {

        if(eventType == null){
            throw new Exception("ThirdPartyEventsType is mandatory");
        }
        String authUserName = TpAuthEnum.USERNAME.getCode();
        String authPassword = TpAuthEnum.PASSWORD.getCode();
        Map<String, String> headerValues = new HashMap<>();

        String encoding = Base64.getEncoder().encodeToString((authUserName + ":" + authPassword).getBytes());
        headerValues.put(HttpHeaders.AUTHORIZATION, "Basic " + encoding);

        switch (eventType){
            case ADD_PHONE_NUMBER:
                String url = TpApiUrlEnum.ADD_PHONE_NUMBER.getUrl();

                HttpPost post = new HttpPost(url);
                populateHeader(headerValues, post);
                StringEntity requestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                post.setEntity(requestEntity);
                HttpHelperResponse<HashMap, StringBuffer> data = fetchResponse(post, HashMap.class, StringBuffer.class, null, null);
                HashMap response = data.getResponse();
                String ans = new Gson().toJson(response);
                return new Gson().toJson(response);
            case CHECK_CONSENT:

                String checkConsentUrl = TpApiUrlEnum.CHECK_CONSENT.getUrl();

                HttpPost checkConsentGetUrl = new HttpPost(checkConsentUrl);
                populateHeader(headerValues, checkConsentGetUrl);
                StringEntity checkConsentRequestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                checkConsentGetUrl.setEntity(checkConsentRequestEntity);
                HttpHelperResponse<HashMap, StringBuffer> data1 = fetchResponse(checkConsentGetUrl, HashMap.class, StringBuffer.class, null, null);
                return new Gson().toJson(data1.getResponse());
            case RESEND_CONSENT_SMS:

                String resendSMSUrl = TpApiUrlEnum.RESEND_CONSENT_SMS.getUrl();

                HttpPost resendSMSGetUrl = new HttpPost(resendSMSUrl);
                populateHeader(headerValues, resendSMSGetUrl);
                StringEntity resendSMSRequestEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
                resendSMSGetUrl.setEntity(resendSMSRequestEntity);
                HttpHelperResponse<HashMap, StringBuffer> resendSMSData = fetchResponse(resendSMSGetUrl, HashMap.class, StringBuffer.class, null, null);
                return new Gson().toJson(resendSMSData.getResponse());

            case NUMBER_LIST:

                String fetchNumberListUrl = TpApiUrlEnum.FETCH_NUMBER_LIST.getUrl();

                HttpPost post2 = new HttpPost(fetchNumberListUrl);
                populateHeader(headerValues, post2);
                HttpHelperResponse<HashMap, StringBuffer> data2 = fetchResponse(post2, HashMap.class, StringBuffer.class, null, null);
                return new Gson().toJson(data2.getResponse());

        }

        return null;

    }

    private void populateHeader(Map<String, String> headerValues, HttpRequestBase get) {
        if (!CollectionUtils.isEmpty(headerValues)) {
            for (Map.Entry<String, String> entry : headerValues.entrySet()) {
                get.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }


    private <T, J> HttpHelperResponse<T, J> fetchResponse(HttpUriRequest request, Class<T> t, Class<J> j, Type type,
                                                          Integer timeOut) {
        HttpHelperResponse<T, J> helperResponse = new HttpHelperResponse<>();
        try {
            Map<String, String> responseHeaderValues = new HashMap<>();

            RequestConfig requestConfig = null;
            if (timeOut != null) {
                requestConfig = RequestConfig.custom().setConnectTimeout(timeOut * 1000).build();
            } else {
                requestConfig = RequestConfig.custom().setConnectTimeout(TIME_OUT * 1000).build();
            }

            HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            long start = System.currentTimeMillis();
            HttpResponse response = client.execute(request);
            logger.info("Status code {}", response.getStatusLine().getStatusCode());
            logger.info("Time taken for request {} is {}", request, (System.currentTimeMillis() - start));
            populateBackHeaderMap(response, responseHeaderValues);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            Gson gObj = builder.create();
            //Gson gObj = new Gson();
            try {
                logger.info("Response Result: {}", result);
                if (t == null && j == null)
                    return helperResponse;
                if (IObjectMapperJson.class.isAssignableFrom(t)) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

                    T resultJson = objectMapper.readValue(result.toString(), t);
                    helperResponse.setResponse(resultJson);

                    helperResponse.setHeaders(responseHeaderValues);
                } else {
                    T resultJson;
                    if (type == null) {

                        if(t != null && String.class.isAssignableFrom(t)) {
                            helperResponse.setResponse((T)result.toString());
                            helperResponse.setHeaders(responseHeaderValues);
                            logger.info("------------------- received http response {} -------------------------", helperResponse);
                            return helperResponse;
                        }
                        JSONParser parser = new JSONParser();
                        JSONObject json = (JSONObject) parser.parse(result.toString());

                        resultJson = gObj.fromJson(json.toString(), t);
                    } else {
                        resultJson = gObj.fromJson(result.toString(), type);
                    }

                    helperResponse.setResponse(resultJson);
                    helperResponse.setHeaders(responseHeaderValues);
                    logger.info("------------------- received http response {} -------------------------", resultJson);
                }

            } catch (Exception ex) {

                Gson gson = new GsonBuilder().setLenient().create();
                try {
                    J resultJson = gson.fromJson(result.toString(), j);
                    helperResponse.setError(resultJson);
                    helperResponse.setHeaders(responseHeaderValues);
                    logger.info("------------------- received http response {} -------------------------", resultJson);
                } catch (JsonIOException | JsonSyntaxException je) {
                    J resultJson = (J) result;
                    helperResponse.setError(resultJson);
                    helperResponse.setHeaders(responseHeaderValues);
                    logger.info("------------------- received http response {} -------------------------", resultJson);
                }
            }
        } catch (ClientProtocolException ce) {
            logger.error("ClientProtocolException: request: {}, Error: {}", request, ce);
            GenericErrorBO ge = new GenericErrorBO();
            ge.setErrorMessage(ce.getMessage());

            J resultJson = new Gson().fromJson(JsonHelper.toJson(ge), j);
            helperResponse.setError(resultJson);
        } catch (IOException ie) {
            GenericErrorBO ge = new GenericErrorBO();
            ge.setErrorMessage(ie.getMessage());

            J resultJson = new Gson().fromJson(JsonHelper.toJson(ge.toString()), j);
            helperResponse.setError(resultJson);
        } catch (Exception e) {
            logger.error("Exception : request: {}, {}", request, e);
            GenericErrorBO ge = new GenericErrorBO();
            ge.setErrorMessage(e.getMessage());

            J resultJson = new Gson().fromJson(JsonHelper.toJson(ge), j);
            helperResponse.setError(resultJson);
        }

        return helperResponse;
    }

    private void populateBackHeaderMap(HttpResponse response, Map<String, String> headerValues) {
        Header[] headers = response.getAllHeaders();
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            headerValues.put("statusCode", Integer.toString(statusCode));
        }
        catch (Exception e) {
            logger.error("Unable to add statusCode as header {}", e);
        }
        if (headers == null || headers.length == 0)
            return;
        for (Header header : headers) {
            headerValues.put(header.getName(), header.getValue());
        }
        logger.info("headerValues: {}", headerValues);
    }
}
