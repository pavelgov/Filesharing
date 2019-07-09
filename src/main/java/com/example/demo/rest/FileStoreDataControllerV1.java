package com.example.demo.rest;

import com.example.demo.rest.dto.FileInfoDto;
import com.example.demo.rest.dto.ResponseCreateCredential;
import com.example.demo.rest.dto.SharedFileDto;
import com.example.demo.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.Principal;
import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class FileStoreDataControllerV1 {

    private final FileService fileService;
    private final static String OK = "OK";
    private final static String ERROR = "ERROR";
    private static final String FILE = "file";
    private static final String NAME = "name";


    @GetMapping("/api/file")
    ResponseEntity getFileOwners(Principal principal) {
        String owner = principal.getName();
        FileInfoDto dto = fileService.getFileInfo(owner);
        if (dto != null) {
            return ResponseEntity.status(200).body(dto);
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @GetMapping("/api/file/{id}")
    ResponseEntity<String> getFile(Principal principal, @PathVariable("id") String fileId) {
        String owner = principal.getName();
        String result = fileService.downloadFile(UUID.fromString(fileId), owner);

        if (result != null) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(400).build();
        }

    }

    @PostMapping("/api/file")
    ResponseEntity uploadFile(MultipartHttpServletRequest request) {
        String name = request.getParameter(NAME);
        MultipartFile file = request.getFile(FILE);
        String owner = request.getUserPrincipal().getName();
        String result = fileService.uploadFile(name, file,owner);
        ResponseCreateCredential resp = new ResponseCreateCredential();
        resp.setDescription(result);

        try {
            UUID.fromString(result);
            resp.setStatus(OK);
            return ResponseEntity.status(200).body(resp);
        } catch (IllegalArgumentException exception) {
            resp.setStatus(ERROR);
            return ResponseEntity.status(400).body(resp);
        }

    }

    @PostMapping("/api/share")
    ResponseEntity shareFile(Principal principal, @RequestBody SharedFileDto sharedFileDto) {
        String owner = principal.getName();

        if (fileService.shareFile(sharedFileDto.getEmail(), sharedFileDto.getFileId(), owner)) {
            ResponseCreateCredential resp = new ResponseCreateCredential();
            resp.setStatus(OK);
            resp.setDescription(sharedFileDto.getFileId().toString());
            return ResponseEntity.status(200).body(resp);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
