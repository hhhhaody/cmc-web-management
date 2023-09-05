package com.example.config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * JWT工具类
 * 该类提供了生成和解析JWT令牌的方法。
 */
@Component
public class JwtUtil {

    // 秘密密钥用于对JWT令牌进行签名
    private final String secret = "YourSecretKey";

    /**
     * 为给定的用户名生成JWT令牌。
     *
     * @param username 要包含在JWT令牌中的用户名
     * @return 生成的JWT令牌
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)   // 设置令牌主题为用户名
                .setIssuedAt(new Date())    // 设置令牌的发行时间
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))   // 设置令牌过期时间为1小时后
                .signWith(SignatureAlgorithm.HS512, secret) // 使用HS512算法和秘密密钥对JWT进行签名
                .compact(); // 构建JWT并将其转化为一个字符串
    }

    /**
     * 从给定的JWT令牌中提取用户名。
     *
     * @param token 要解析的JWT令牌
     * @return JWT令牌中包含的用户名
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)  // 使用相同的秘密密钥进行解析
                .parseClaimsJws(token)  // 解析传入的JWT令牌
                .getBody()  // 获取令牌体
                .getSubject();  // 从令牌体中获取主题，即用户名
    }
}

