package com.example.grocery.api.auth.register;

import com.example.grocery.api.user.User;
import com.example.grocery.api.user.UserDTO;
import com.example.grocery.enums.RoleType;
import com.example.grocery.utils.Hash;

import java.util.ArrayList;
import java.util.List;

public class RegisterMapper {
    public static UserDTO registerToUser(RegisterDTO registerDTO) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(registerDTO.getFirstName());
        userDTO.setLastName(registerDTO.getLastName());
        userDTO.setEmail(registerDTO.getEmail());
        userDTO.setPhone(registerDTO.getPhone());
        userDTO.setBirthDate(registerDTO.getBirthDate());
        userDTO.setPassword(Hash.sha512(registerDTO.getPassword()));

        List<RoleType> roles = new ArrayList<>();
        roles.add(RoleType.CUSTOMER);
        userDTO.setRoles(roles);

        return userDTO;
    }
}
