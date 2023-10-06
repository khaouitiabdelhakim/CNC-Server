package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.GradeEntryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeEntryAgentRepository extends JpaRepository<GradeEntryAgent, Long> {
  Optional<GradeEntryAgent> findByIdUser(Long userId);

}
