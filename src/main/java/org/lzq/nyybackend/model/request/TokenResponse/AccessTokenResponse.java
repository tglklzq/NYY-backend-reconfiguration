package org.lzq.nyybackend.model.request.TokenResponse;

import lombok.Data;

@Data
public class AccessTokenResponse {
    private String accessToken;
    public AccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
