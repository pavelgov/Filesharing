package com.example.demo.service;

import com.example.demo.rest.dto.AuthDataDto;
import com.example.demo.rest.dto.CredentialDto;

public interface AuthenticationService{


        String hashPassword(String textPassword);

        String createCredential(CredentialDto credentialDto);

}
