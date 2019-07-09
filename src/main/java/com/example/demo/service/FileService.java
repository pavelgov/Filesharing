package com.example.demo.service;

import com.example.demo.rest.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    String uploadFile(String name, MultipartFile file, String owner);

    String downloadFile(UUID fileId,String owner);

    boolean shareFile(String email, UUID fileId, String owner);

    FileInfoDto getFileInfo(String owner);
}
