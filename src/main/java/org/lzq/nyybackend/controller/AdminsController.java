package org.lzq.nyybackend.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import org.lzq.nyybackend.config.MenuConfig;
import org.lzq.nyybackend.domain.Admins;
import org.lzq.nyybackend.exception.BusinessException;
import org.lzq.nyybackend.interceptor.JwtTokenInterceptor;
import org.lzq.nyybackend.model.dto.*;
import org.lzq.nyybackend.model.request.LoginAndRegister.AdminsLoginRequest;
import org.lzq.nyybackend.model.request.LoginAndRegister.AdminsRegisterRequest;
import org.lzq.nyybackend.model.request.TokenResponse.AccessTokenResponse;
import org.lzq.nyybackend.model.request.TokenResponse.RefreshTokenRequest;
import org.lzq.nyybackend.service.AdminsService;
import org.lzq.nyybackend.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;



@RestController
@RequestMapping("/api/user")
public class AdminsController {
    @Autowired
    private AdminsService adminsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    Integer FinalrolePermissionId=1;
    @PostMapping("/login")
    public ApiResponse<Object> login(@RequestBody AdminsLoginRequest adminsLoginRequest, HttpServletRequest request) throws JsonProcessingException {
        if (adminsLoginRequest == null) {return ResultUtils.error(ResponseStatus.NOT_LOGIN);}
        String Email = adminsLoginRequest.getEmail();
        String Password_hash = adminsLoginRequest.getPasswordHash();
        //System.out.println("Email: " + Email + " Password_hash: " + Password_hash);
        if (StringUtils.isAnyBlank(Email, Password_hash)) {return ResultUtils.error(ResponseStatus.PARAMS_ERROR);}
        Admins admins = adminsService.selectByEmailToLogin(Email, Password_hash,request);
        //生成token
        String accessToken = JwtTokenUtil.generateAccessToken(Email);
        String refreshToken = JwtTokenUtil.generateRefreshToken(Email);
        //获取角色权限ID
        int rolePermissionId = admins.getRole_permission_id();
        //获取菜单详情
        List<Map<String, Object>> menuDetails = MenuConfig.getMenuForRole(rolePermissionId);
        //封装响应数据，包括用户信息与菜单信息和token
        System.out.println("accessToken:"+accessToken);
        System.out.println("refreshToken"+refreshToken);
        LoginResponse loginResponse = new LoginResponse(admins, menuDetails, accessToken,refreshToken);
        return ResultUtils.success(loginResponse);
    }
    @PostMapping("/logout")
    public ApiResponse<Object> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResponseStatus.PARAMS_ERROR);
        }
        int result = adminsService.Logout(request);
        return ResultUtils.success(result);
    }
    @PostMapping("/refreshToken")
    public ApiResponse<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        //System.out.println("refreshToken:"+refreshToken);
        if (StringUtils.isAnyBlank(refreshToken)) {
            return ResultUtils.error(ResponseStatus.PARAMS_ERROR);
        }
        if(JwtTokenUtil.isTokenExpired(refreshToken)){
            //System.out.println("refreshToken过期");
            return ResultUtils.error(ResponseStatus.PARAMS_ERROR);
        }
        else{
            //System.out.println("refreshToken未过期");
            // 刷新token
            String accessToken = JwtTokenUtil.refreshToken(refreshToken);
            //System.out.println("AdminsController刷新token: " + accessToken);
            return ResultUtils.success(new AccessTokenResponse(accessToken));

        }
    }



    @PostMapping("/register")
    public ApiResponse<Object> register(@RequestBody AdminsRegisterRequest adminsRegisterRequest) {
        if (adminsRegisterRequest == null) {return ResultUtils.error(ResponseStatus.PARAMS_ERROR);}
        if (StringUtils.isAnyBlank(adminsRegisterRequest.getUsername(), adminsRegisterRequest.getEmail(), adminsRegisterRequest.getPhoneNumber(), adminsRegisterRequest.getPasswordHash()))
            return ResultUtils.error(ResponseStatus.PARAMS_ERROR);
        String username = adminsRegisterRequest.getUsername();
        String email = adminsRegisterRequest.getEmail();
        String phoneNumber = adminsRegisterRequest.getPhoneNumber();
        Integer rolePermissionId = FinalrolePermissionId;
        String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String hashPassword = adminsRegisterRequest.getPasswordHash();
        int  result = adminsService.insertRegister(username, email, phoneNumber, rolePermissionId,
                createdAt, hashPassword);
        if(result<=0) return ResultUtils.error(ResponseStatus.PARAMS_ERROR);
        return ResultUtils.success(null);
    }

    @PostMapping("/personalInformation")
    public ApiResponse<Object> personalInformation(@RequestBody Admins admins, HttpServletRequest request) {
        System.out.println("adminsEmail:"+admins.getEmail());
        return ResultUtils.success(adminsService.selectByEmail(admins.getEmail()));
    }
}
