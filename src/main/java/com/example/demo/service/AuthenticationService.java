package com.example.demo.service;

import com.example.demo.rest.dto.CredentialDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity createCredential(CredentialDto credentialDto);

}
