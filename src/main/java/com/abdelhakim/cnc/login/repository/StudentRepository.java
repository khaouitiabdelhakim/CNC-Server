package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Optional<Student> findByIdUser(Long userId);

  List<Student> findByEstProfilValideFalse();

  List<Student> findByEstProfilValideTrue();


}
