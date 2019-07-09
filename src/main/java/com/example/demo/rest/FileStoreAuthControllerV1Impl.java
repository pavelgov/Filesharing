package com.example.demo.rest;

import com.example.demo.rest.dto.CredentialDto;
import com.example.demo.rest.dto.ResponseCreateCredential;
import com.example.demo.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController()
@AllArgsConstructor
public class FileStoreAuthControllerV1Impl {

    private final AuthenticationService authenticationService;
    private final static String OK = "OK";
    private final static String ERROR = "ERROR";


    @PostMapping("/register")
    public ResponseEntity createCredential(@RequestBody @Valid CredentialDto dto) {
        return authenticationService.createCredential(dto);

    }

}
