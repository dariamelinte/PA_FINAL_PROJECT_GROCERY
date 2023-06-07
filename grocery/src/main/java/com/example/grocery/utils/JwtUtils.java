package com.example.grocery.utils;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserService;
import com.example.grocery.enums.RoleType;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@ComponentScan("com.example.grocery.api")
public class JwtUtils {
    @Autowired
    UserService userService;
    private final String key = "grocery_jwt_key";

    public String generateToken(String userId) {
        Date expiryDate = new Date(new Date().getTime() + 1000 * 60 * 60 * 3); // Token valid for 3 hour

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String parseJwt(String bearerToken) {
        if (!bearerToken.startsWith("Bearer ")) {
            return null;
        }

        return bearerToken.substring(7, bearerToken.length());
    }

    public String getUserIdFromJwtToken(String bearerToken) {
        String token = parseJwt(bearerToken);

        if (token == null || !validateJwtToken(token)) {
            return null;
        }

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String bearerToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(bearerToken);
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

    public boolean isUserAuthorized(String bearerToken) {
        String userId = getUserIdFromJwtToken(bearerToken);

        if (userId == null) {
            return false;
        }

        User user = userService.getById(userId);

        if (user == null) {
            return false;
        }

        return Objects.equals(user.getJwt(), parseJwt(bearerToken));
    }

    public boolean isAdminAuthorized(String bearerToken) {
        if (!isUserAuthorized(bearerToken)) {
            return false;
        }

        User user = userService.getById(getUserIdFromJwtToken(bearerToken));

        return user.getRoles().contains(RoleType.ADMIN);
    }
}
