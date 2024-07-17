package org.lzq.nyybackend.model.vo;

import lombok.Data;

@Data
public class AdminsVo {
    public String username;
    public String email;
    public String passwordHash;
    public String phoneNumber;
    public Integer rolePermissionId;

}

