package com.abdelhakim.cnc.login.repository;

import java.util.List;
import java.util.Optional;

import com.abdelhakim.cnc.login.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abdelhakim.cnc.login.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  List<User> findAllByRole(ERole role);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<User> findUserById(Long id);

  @Query("SELECT u FROM User u WHERE u.id IN (SELECT DISTINCT n.idEtudiant FROM Note n WHERE n.idAgent = :agentId)")
  List<User> findStudentsWithNotes(@Param("agentId") Long agentId);


  // Custom query to find students without notes
  @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT DISTINCT n.idEtudiant FROM Note n WHERE n.idAgent = :agentId)")
  List<User> findStudentsWithoutNotes(@Param("agentId") Long agentId);

  @Query("SELECT u FROM User u WHERE u.id IN (SELECT DISTINCT n.idEtudiant FROM Note n WHERE  n.note = -1)")
  List<User> findStudentsWithFalseNotes(@Param("agentId") Long agentId);


}
