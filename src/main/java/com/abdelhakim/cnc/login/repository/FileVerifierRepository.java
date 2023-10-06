package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.FileVerifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileVerifierRepository extends JpaRepository<FileVerifier, Long> {
  Optional<FileVerifier> findByIdUser(Long userId);

}
