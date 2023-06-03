package com.example.grocery.api.user;

import com.example.grocery.utils.Hash;

public class UserMapper {
    public static User dtoToEntity(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setBirthDate(userDTO.getBirthDate());
        user.setHashedPassword(Hash.sha512(userDTO.getPassword()));
        user.setJwt(userDTO.getJwt());
        user.setRoles(userDTO.getRoles());
        return user;
    }
}
