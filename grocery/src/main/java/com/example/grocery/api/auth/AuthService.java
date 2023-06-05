package com.example.grocery.api.auth;


import com.example.grocery.api.auth.login.LoginDTO;
import com.example.grocery.api.auth.register.RegisterDTO;
import com.example.grocery.api.auth.register.RegisterMapper;
import com.example.grocery.api.user.*;
import com.example.grocery.utils.Hash;
import com.example.grocery.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.example.grocery.utils.JwtUtils.generateToken;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    public Response<User> login(LoginDTO loginDTO) {
        Response<User> response = new Response<>();
        User user = userService.getByEmail(loginDTO.getEmail());

        if (user == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage("User does not exist.");
            return response;
        }

        String hashedPassword = Hash.sha512(loginDTO.getPassword());

        if (!Objects.equals(hashedPassword, user.getHashedPassword())) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setMessage("User does not exist.");
            return response;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setJwt(generateToken(user.getId()));

        userService.update(user.getId(), userDTO, false);

        response.setStatus(HttpStatus.OK);
        response.setMessage("Login successful.");
        response.setData(userService.getByEmail(loginDTO.getEmail()));
        return response;
    }


    public Response<User> register(RegisterDTO registerDTO) {
        Response<User> response = new Response<>();

        User user = userService.getByEmail(registerDTO.getEmail());

        if (user != null) {
            response.setStatus(HttpStatus.CONFLICT);
            response.setMessage("Email already used. Please log in.");
            return response;
        }

        userService.create(RegisterMapper.registerToUser(registerDTO));
        user = userService.getByEmail(registerDTO.getEmail());

        if (user == null) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Something went wrong, please try again.");
            return response;
        }


        UserDTO userDTO = new UserDTO();
        userDTO.setJwt(generateToken(user.getId()));

        userService.update(user.getId(), userDTO, false);

        response.setStatus(HttpStatus.OK);
        response.setMessage("Register successful.");
        response.setData(userService.getByEmail(registerDTO.getEmail()));
        return response;
    }

}
