package com.abdelhakim.cnc.login.repository;

import java.util.List;
import java.util.Optional;

import com.abdelhakim.cnc.login.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.abdelhakim.cnc.login.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  List<User> findAllByRole(ERole role);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<User> findUserById(Long id);

  @Query("SELECT DISTINCT u FROM User u JOIN Note n ON u.id = n.idEtudiant")
  List<User> findStudentsWithNotes();

  // Custom query to find students without notes
  @Query("SELECT u FROM User u WHERE u.role = 'STUDENT' AND u.id NOT IN (SELECT DISTINCT n.idEtudiant FROM Note n)")
  List<User> findStudentsWithoutNotes();
}
