package com.example.demo.repository;

import com.example.demo.model.SharedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<SharedFile, UUID> , JpaSpecificationExecutor<SharedFile> {
    SharedFile findByFileName(String fileName);
    List<SharedFile> findByOwner(String owner);
}
