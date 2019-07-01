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

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class FileStoreDataControllerV1 {

    private final FileService fileService;
    private final static String OK = "OK";
    private final static String ERROR = "ERROR";


    @GetMapping("/api/file")
    ResponseEntity getFileOwners(@RequestHeader(name = "userId", required = true) String userId) {
       FileInfoDto dto = fileService.getFileInfo(UUID.fromString(userId));
        if (dto != null) {
            return ResponseEntity.status(200).body(dto);
        } else {
            return ResponseEntity.status(400).build();
        }
    }


    @GetMapping("/api/file/{id}")
    ResponseEntity<String> getFile(@RequestHeader(name = "userId", required = true) String userId, @PathVariable("id") String fileId) {
        String result = fileService.downloadFile(UUID.fromString(fileId), UUID.fromString(userId));

        if (result != null) {
            return ResponseEntity.status(200).body(result);
        } else {
            return ResponseEntity.status(400).build();
        }

    }

    @PostMapping("/api/file")
    ResponseEntity uploadFile(@RequestParam("name") String name,
                              @RequestParam("file") MultipartFile file,
                              @RequestHeader(name = "userId", required = true) String userId) {

        String result = fileService.uploadFile(name, file, UUID.fromString(userId));
        ResponseCreateCredential resp = new ResponseCreateCredential();
        resp.setDescription(result);

        try{
            UUID.fromString(result);
            resp.setStatus(OK);
            return ResponseEntity.status(200).body(resp);
        } catch (IllegalArgumentException exception){
            resp.setStatus(ERROR);
            return ResponseEntity.status(400).body(resp);
        }

    }

    @PostMapping("/api/share")
    ResponseEntity shareFile(@RequestHeader(name = "userId", required = true) String userId, @RequestBody SharedFileDto sharedFileDto) {
        if (fileService.shareFile(sharedFileDto.getEmail(), sharedFileDto.getFileId(), UUID.fromString(userId))) {
            ResponseCreateCredential resp = new ResponseCreateCredential();
            resp.setStatus(OK);
            resp.setDescription(sharedFileDto.getFileId().toString());
            return ResponseEntity.status(200).body(resp);
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
