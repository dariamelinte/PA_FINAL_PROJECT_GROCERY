package com.example.grocery.user;

import com.example.grocery.enums.RoleTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Getter @Id @GeneratedValue private Long id;
    @Getter @Setter private String email;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String phone;
    @Getter @Setter private Date birthDate;
    @Getter @Setter private String hashedPassword;
    @Getter @Setter private String jwt;
    @Getter @Setter private List<RoleTypes> roles;
}
