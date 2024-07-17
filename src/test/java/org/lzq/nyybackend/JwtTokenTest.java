package org.lzq.nyybackend;

import org.lzq.nyybackend.util.JwtTokenUtil;

public class JwtTokenTest {
    public static void main(String[] args) throws InterruptedException {
        String token = JwtTokenUtil.generateAccessToken("testUser");
        String refreshToken = JwtTokenUtil.generateRefreshToken("testUser");
        System.out.println("Generated Token: " + token);
        System.out.println("Generated Refresh Token: " + refreshToken);

        // 等待超过5秒钟
        Thread.sleep(6000);

        // 验证 token 是否过期
        boolean isExpired = JwtTokenUtil.isTokenExpired(token);
        System.out.println("Token是否过期: " + isExpired); // 应该输出 true

        // 刷新 token
        String newToken = JwtTokenUtil.refreshToken(refreshToken);
        boolean isNewTokenExpired = JwtTokenUtil.isTokenExpired(newToken);
        System.out.println("新Token是否过期: " + isNewTokenExpired); // 应该输出 false
        Thread.sleep(6000);
        boolean isNewTokenExpired2 = JwtTokenUtil.isTokenExpired(newToken);
        System.out.println("新Token是否过期: " + isNewTokenExpired2); // 应该输出 false
    }
}

