package com.example.grocery.api.user;

import com.example.grocery.enums.RoleType;
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
    private String password;
    private String jwt;
    private List<RoleType> roles;
}
