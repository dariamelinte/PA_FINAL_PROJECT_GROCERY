package com.example.grocery.api.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Date birthDate;
}