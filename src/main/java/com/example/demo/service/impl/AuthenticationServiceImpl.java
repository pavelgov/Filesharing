package com.example.demo.service.impl;

import com.example.demo.model.Credential;
import com.example.demo.repository.CredentialRepository;
import com.example.demo.rest.dto.CredentialDto;
import com.example.demo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {


    private final CredentialRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String EMPTY_DTO = "CredentialDto is empty";
    private final String ERROR_SAVE = "Error save credential";


    public String hashPassword(String plainTextPassword) {
        return passwordEncoder.encode(plainTextPassword);
    }

    @Override
    public String createCredential(CredentialDto credentialDto) {
        if (credentialDto != null) {
            if (repository.findByUsername(credentialDto.getEmail()) == null) {
                Credential credential = new Credential();
                credential.setUsername(credentialDto.getEmail());
                credential.setPassword(passwordEncoder.encode(credentialDto.getPassword()));
                credential.setActive(true);
                credential.setRoles(credentialDto.getRoles());
                try {
                    repository.save(credential);

                } catch (Exception ex) {
                    log.error("Error save credential: {}", ex.getMessage());
                    return ERROR_SAVE;

                }
                log.info("Credential saved successfull: {}", credential);
                return credential.getId().toString();
            }
        }
        return EMPTY_DTO;
    }


}
