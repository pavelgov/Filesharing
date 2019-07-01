package com.example.demo.service.impl;

import com.example.demo.model.Credential;
import com.example.demo.model.SharedFile;
import com.example.demo.model.SharedFileHolder;
import com.example.demo.repository.CredentialRepository;
import com.example.demo.repository.FileRepository;
import com.example.demo.repository.SharedFileHolderRepository;
import com.example.demo.rest.dto.FileInfoDto;
import com.example.demo.service.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final static String FILE_NOT_LOADED = "File not loaded:";
    private final static String FILE_EXIST = "File already exist, please rename file:";

    @Value("${uploads.path}")
    private String uploadPath;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private SharedFileHolderRepository holderRepository;
    @Autowired
    private CredentialRepository credentialRepository;

    @Override
    public String uploadFile(String fileName, MultipartFile file, UUID userId) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            SharedFile sharedFile = new SharedFile();
            sharedFile.setId(UUID.randomUUID());
            sharedFile.setFileName(fileName);
            sharedFile.setOwner(userId);

            try {
                if (fileRepository.findByFileName(fileName) == null) {

                    // Get the file and save it
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadPath +"\\" + fileName);
                    Files.write(path, bytes);
                    fileRepository.save(sharedFile);
                } else {
                    return FILE_EXIST;
                }

            } catch (IOException e) {
                log.error("Error save file: {}", fileName);
            }
            log.info("File saved successfull: {} ", fileName);

            return sharedFile.getId().toString();
        } else {
            return FILE_NOT_LOADED;
        }
    }

    @Override
    public String downloadFile(UUID fileId, UUID userId) {
        //1.По ид файла найти владельца
        SharedFile sharedFile = fileRepository.findById(fileId).get();
        StringBuilder builder = new StringBuilder();

        if (userId.equals(sharedFile.getOwner()) || sharedFile.getFileHolders().contains(userId)) {
            try {
                Files.lines(Paths.get(uploadPath + "/" + sharedFile.getFileName()), StandardCharsets.UTF_8).forEach(s -> builder.append(s));
            } catch (IOException e) {
                log.error("Couldn't read file:{}", sharedFile.getFileName());
            }

        }
        return builder.toString();
    }

    @Override
    public boolean shareFile(String email, UUID fileId, UUID owner) {
        if (fileRepository.findByOwner(owner).stream().map(SharedFile::getOwner).collect(Collectors.toList()).contains(owner)) {//TODO

            SharedFile sharedFile = fileRepository.findById(fileId).get();
            Credential credential = credentialRepository.findByEmail(email);

            if (sharedFile != null) {
                SharedFileHolder fileHolder = new SharedFileHolder();
                fileHolder.setFileHolderId(credential.getUserId());
                fileHolder.setFileId(sharedFile);
                SharedFileHolder savedFileHolder = holderRepository.save(fileHolder);
                return true;
            }

        }
        return false;
    }

    @Override
    public FileInfoDto getFileInfo(UUID owner) {
        FileInfoDto fileInfoDto = new FileInfoDto();
        List<SharedFile> sharedFiles = fileRepository.findByOwner(owner);

        fileInfoDto.setOwned(
                sharedFiles.stream().map(SharedFile::getFileName).collect(Collectors.toList()));
        List<SharedFileHolder> sharedFileHolders = holderRepository.findByfileHolderId(owner);

        List<SharedFile> SharedFilelist = sharedFileHolders.stream().map(SharedFileHolder::getFileId).collect(Collectors.toList());
        fileInfoDto.setShared(SharedFilelist.stream().map(SharedFile::getFileName).collect(Collectors.toList()));
        return fileInfoDto;
    }
}
