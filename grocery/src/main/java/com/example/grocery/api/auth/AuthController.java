package com.example.grocery.api.auth;

import com.example.grocery.api.auth.login.LoginDTO;
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
        HttpStatus status = response.getStatusCode();
        response.setStatusCode(null);

        return new ResponseEntity<>(response, status);
    }
}
