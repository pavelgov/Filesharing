package com.example.demo.repository;

import com.example.demo.model.SharedFileHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SharedFileHolderRepository extends JpaRepository<SharedFileHolder, UUID> , JpaSpecificationExecutor<com.example.demo.model.SharedFileHolder> {
    List<SharedFileHolder> findByfileHolderId(String fileHolderId);
}
