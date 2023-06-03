package com.example.grocery.user;

import com.example.grocery.enums.RoleTypes;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
    private String hashedPassword;
    private String jwt;
    private List<RoleTypes> roles;
}
