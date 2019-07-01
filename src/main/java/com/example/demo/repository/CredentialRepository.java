package com.example.demo.repository;

import com.example.demo.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, UUID>, JpaSpecificationExecutor<Credential> {
    Credential findByUsername(String username);
}
