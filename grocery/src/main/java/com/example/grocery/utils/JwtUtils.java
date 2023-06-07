package com.example.grocery.utils;

import com.example.grocery.api.grocery.Grocery;
import com.example.grocery.api.grocery.GroceryRepository;
import com.example.grocery.api.grocery.GroceryService;
import com.example.grocery.api.productGroceries.ProductGroceries;
import com.example.grocery.api.productGroceries.ProductGroceriesRepository;
import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserRepository;
import com.example.grocery.api.user.UserService;
import com.example.grocery.enums.RoleType;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Role;
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
    UserRepository userRepository;
    @Autowired
    GroceryRepository groceryRepository;
    @Autowired
    ProductGroceriesRepository productGroceriesRepository;
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

    public boolean isAuthorized(String bearerToken) {
        String userId = getUserIdFromJwtToken(bearerToken);

        if (userId == null) {
            return false;
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return false;
        }

        return Objects.equals(user.getJwt(), parseJwt(bearerToken));
    }

    public boolean isRoleAuthorized(String bearerToken, RoleType roleType) {
        if (!isAuthorized(bearerToken)) {
            return false;
        }

        User user = userRepository.findById(getUserIdFromJwtToken(bearerToken)).orElse(null);

        return user.getRoles().contains(roleType);
    }

    public boolean isGroceryAuthorized(String groceryId, String bearerToken) {
        if (isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            return true;
        }

        User user = userRepository.findById(getUserIdFromJwtToken(bearerToken)).orElse(null);
        Grocery grocery = groceryRepository.findById(groceryId).orElse(null);

        if (user == null || grocery == null) {
            return false;
        }

        return Objects.equals(grocery.getUserId(), user.getId());
    }

    public boolean isProductGroceryAuthorize(String bearerToken, String productGroceryId){
        if (isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            return true;
        }

        ProductGroceries productGrocery = productGroceriesRepository.findById(productGroceryId).orElse(null);
        if(productGrocery == null) return false;

        var groceryId = productGrocery.getGroceryId();
        Grocery grocery = groceryRepository.findById(groceryId).orElse(null);
        if(grocery == null) return false;

        User user = userRepository.findById(getUserIdFromJwtToken(bearerToken)).orElse(null);
        if(user == null) return false;

        return Objects.equals(grocery.getUserId(), user.getId());
    }
    
    public boolean isUserAuthorized(String userId, String bearerToken) {
        if (!isRoleAuthorized(bearerToken, RoleType.ADMIN)) {
            return false;
        }

        User user = userRepository.findById(getUserIdFromJwtToken(bearerToken)).orElse(null);

        if (user == null) {
            return false;
        }

        return Objects.equals(user.getId(), userId);
    }
}
