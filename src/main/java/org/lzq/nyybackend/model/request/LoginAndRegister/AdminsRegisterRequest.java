package org.lzq.nyybackend.model.request.LoginAndRegister;

import lombok.Data;

@Data
public class AdminsRegisterRequest {
    public String username;
    public String email;
    public String passwordHash;
    public String phoneNumber;
    public Integer rolePermissionId;
}
