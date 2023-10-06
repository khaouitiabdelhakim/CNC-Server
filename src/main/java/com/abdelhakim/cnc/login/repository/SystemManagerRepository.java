package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.SystemManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SystemManagerRepository extends JpaRepository<SystemManager, Long> {
  Optional<SystemManager> findByIdUser(Long userId);

}
