package org.lzq.nyybackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // 确保密钥一致
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 5000; // 过期时间
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7天

    /**
     * 生成access_token
     * @param Email
     * @return
     */
    public static String generateAccessToken(String Email) {
        return Jwts.builder()
                .setSubject(Email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 生成refresh_token
     * @param Email
     * @return
     */
    public static String generateRefreshToken(String Email) {
        return Jwts.builder()
                .setSubject(Email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * 验证token并获取其claims
     * @param token 需要验证的token
     * @return 如果token有效返回claims，否则返回null
     */
    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return null; // token过期
        } catch (Exception e) {
            return null; // token无效
        }
    }

    /**
     * 验证token是否过期
     * @param token 需要验证的token
     * @return 如果token过期返回true，否则返回false
     */
    public static boolean isTokenExpired(String token) {
        Claims claims = validateToken(token);
        return claims == null || claims.getExpiration().before(new Date());
    }

    /**
     * 验证token是否正确
     * @param token
     * @return
     */
    public static boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证 token 是否有效且未过期
     *
     * @param token 要验证的 token
     * @return 如果 token 有效且未过期，返回 true，否则返回 false
     */
    public static boolean isTokenValidAndNotExpired(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expiration = claims.getExpiration();
            return !expiration.before(new Date());
        } catch (Exception e) {
            // 其他异常，token 无效
            return false;
        }
    }

    /**
     * 获取token中的Email
     * @param token
     * @return
     */
    public static String getEmailFromToken(String token) {
        Claims claims = validateToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 刷新token
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        Claims claims = validateToken(token);
        if (claims != null) {
            String Email = claims.getSubject();
            return generateAccessToken(Email);
        }
        return null;
    }

}