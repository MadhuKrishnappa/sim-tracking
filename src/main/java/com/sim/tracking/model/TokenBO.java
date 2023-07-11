package com.sim.tracking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenBO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6408261573178728481L;

    private String accessToken;
    private String refreshToken;
    private String tokenId;



    public TokenBO() {
    }

    public TokenBO(String accessToken, String refreshToken,String tokenId) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenId = tokenId;
    }
}
