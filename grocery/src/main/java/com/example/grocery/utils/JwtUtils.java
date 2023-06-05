package com.example.grocery.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

import java.util.Date;

@Data
public class JwtUtils {
    private static final String key = "grocery_jwt_key";

    public static String generateToken(String userId) {
        Date expiryDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 3); // Token valid for 3 hour

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
}
