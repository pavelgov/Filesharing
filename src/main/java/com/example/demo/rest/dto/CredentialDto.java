package com.example.demo.rest.dto;

import com.example.demo.model.Role;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {

    private String email;
    private String password;
    private Set<Role> roles;
}
