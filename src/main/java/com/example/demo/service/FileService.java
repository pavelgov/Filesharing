package com.example.demo.service;

import com.example.demo.rest.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileService {
    String uploadFile(String name, MultipartFile file, UUID userId);

    String downloadFile(UUID fileId,UUID userId);

    boolean shareFile(String email, UUID fileId, UUID owner);

    FileInfoDto getFileInfo(UUID owner);
}
