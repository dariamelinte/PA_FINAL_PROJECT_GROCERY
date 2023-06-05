package com.example.grocery.api.auth;

import com.example.grocery.api.auth.login.LoginDTO;
import com.example.grocery.api.auth.register.RegisterDTO;
import com.example.grocery.api.user.User;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Response<User>> login(@RequestBody LoginDTO loginDTO) {
        Response<User> response = authService.login(loginDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/register")
    public ResponseEntity<Response<User>> register(@RequestBody RegisterDTO registerDTO) {
        Response<User> response = authService.register(registerDTO);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<Response<User>> logout(@RequestHeader("Authorization") String bearerToken) {
        Response<User> response = authService.logout(bearerToken);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
