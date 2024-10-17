package com.project.utils;


import com.project.table.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {
    private static final String SECRET_KEY = "your_secret_key"; // Replace with a secure key

    public static String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userInfo.getUsername());
        claims.put("fullName", userInfo.getFullName());
        claims.put("email", userInfo.getEmail());
        claims.put("userId", userInfo.getId());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userInfo.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}