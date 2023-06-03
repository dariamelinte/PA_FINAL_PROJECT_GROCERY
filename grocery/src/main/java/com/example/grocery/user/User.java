package com.example.grocery.user;

import com.example.grocery.enums.RoleType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "users")
public class User {
    @Id @GeneratedValue private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
    private String hashedPassword;
    private String jwt;
    private List<RoleType> roles;
}
