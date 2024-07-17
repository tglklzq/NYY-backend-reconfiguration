package org.lzq.nyybackend.model.request.LoginAndRegister;

import lombok.Data;

@Data
public class AdminsLoginRequest {
    private String Email;
    private String passwordHash;
}
