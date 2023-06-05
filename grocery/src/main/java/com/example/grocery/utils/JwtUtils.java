package com.example.grocery.utils;

import io.jsonwebtoken.*;
import lombok.Data;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
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

    public static String parseJwt(String bearerToken) {
        if (!bearerToken.startsWith("Bearer ")) {
            return null;
        }

        return bearerToken.substring(7, bearerToken.length());
    }

    public static String getUserIdFromJwtToken(String bearerToken) {
        String token = parseJwt(bearerToken);

        if (token == null) {
            return null;
        }

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public static boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }

        return false;
    }
}
