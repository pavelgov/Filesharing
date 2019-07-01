package com.example.demo.service;

import com.example.demo.rest.dto.CredentialDto;

public interface AuthenticationService {

    String createCredential(CredentialDto credentialDto);

}
