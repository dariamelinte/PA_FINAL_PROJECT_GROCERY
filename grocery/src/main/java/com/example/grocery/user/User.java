package com.example.grocery.user;

import com.example.grocery.enums.RoleTypes;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id @GeneratedValue private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
    private String hashedPassword;
    private String jwt;
    private List<RoleTypes> roles;
}