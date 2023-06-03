package com.example.grocery.api.user;

import com.example.grocery.enums.RoleType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
    private String hashedPassword;
    private String jwt;
    private List<RoleType> roles;
}
