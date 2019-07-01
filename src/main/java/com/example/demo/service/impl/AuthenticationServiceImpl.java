package com.example.demo.service.impl;

import com.example.demo.rest.dto.AuthDataDto;
import com.example.demo.rest.dto.CredentialDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.demo.model.Credential;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.repository.CredentialRepository;
import com.example.demo.service.AuthenticationService;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final CredentialRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String EMPTY_DTO ="credentialDto is empty";


    public String hashPassword(String plainTextPassword) {
        return passwordEncoder.encode(plainTextPassword);
    }

    @Override
    public String createCredential(CredentialDto credentialDto) {
        if (credentialDto != null) {
            if (repository.findByEmail(credentialDto.getEmail()) == null) {
                Credential credential = new Credential();
                credential.setEmail(credentialDto.getEmail());
                credential.setHashPass(passwordEncoder.encode(credentialDto.getPassword()));
                credential.setUserId(UUID.randomUUID());
                credential.setRoles(credentialDto.getRoles());
                try {
                   repository.save(credential);

                } catch (Exception ex) {
                    log.error("Error save credential: {}", ex.getMessage());
                    return ex.getMessage();
                }
                log.info("Credential saved successfull: {}", credential);
                return credential.getUserId().toString();
            }
        }
        return EMPTY_DTO;
    }


}
