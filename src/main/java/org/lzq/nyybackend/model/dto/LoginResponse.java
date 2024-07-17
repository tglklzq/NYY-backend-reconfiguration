package org.lzq.nyybackend.model.dto;

import lombok.Data;
import org.lzq.nyybackend.domain.Admins;

import java.util.List;
import java.util.Map;

@Data
public class LoginResponse {
    private Admins adminInfo;
    private List<Map<String, Object>> menuDetails;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(Admins adminInfo, List<Map<String, Object>> menuDetails, String accessToken, String refreshToken) {
        this.adminInfo = adminInfo;
        this.menuDetails = menuDetails;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
