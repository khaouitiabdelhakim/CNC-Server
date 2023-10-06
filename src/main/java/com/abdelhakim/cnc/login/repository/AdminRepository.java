package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
  Optional<Admin> findByIdUser(Long userId);

}
