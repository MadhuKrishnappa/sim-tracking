package com.sim.tracking.utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;

public class JWTUtils {

    public static String createToken(String payload, String key) throws Exception {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(key);
            String token = JWT.create()
                    .withClaim(UUID.randomUUID().toString(), new Date())
                    .sign(algorithmHS);

            return token;
        } catch (Exception e) {
            throw new Exception("Error Creating token");
        }
    }

    /* public static String createAccessOrRefreshToken(UserTokenObject user, CompanyTokenObject company,
                                                    LocationTokenObject location, String key, Integer expiryInDays) throws TokenException {
        try
        {
            Algorithm algorithmHS = Algorithm.HMAC256(key);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, expiryInDays);
            Gson gson = new Gson();
            String token = JWT.create().withClaim(Attributes.USER.getName(), gson.toJson(user))
                    .withClaim(Attributes.COMPANY.getName(), gson.toJson(company))
                    .withClaim(Attributes.LOCATION.getName(), gson.toJson(location)).withExpiresAt(cal.getTime())
                    .sign(algorithmHS);

            return token;
        }catch(Exception e){
            throw new TokenException("Error generating token");
        }
    } */

    public static Map<String, Claim> checkToken(String token, String key) throws Exception {
        try {
            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(key)).build().verify(token);
            return decoded.getClaims();
        } catch (Exception e) {
            throw new Exception("Error decoding token");
        }
    }

    public static Map<String, Claim> getClaims(String token, String key) throws Exception {
        try {
            return JWT.require(Algorithm.HMAC256(key)).build().verify(token).getClaims();
        } catch (Exception e) {
            throw new Exception("Error decoding token");
        }
    }
}
