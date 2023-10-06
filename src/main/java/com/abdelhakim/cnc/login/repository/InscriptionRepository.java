package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.ERole;
import com.abdelhakim.cnc.login.models.Inscription;
import com.abdelhakim.cnc.login.models.Student;
import com.abdelhakim.cnc.login.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Long> {

    Inscription findByIdStudent(Long id);
    List<Inscription> findByEstDossierEcritValideTrue();

    List<Inscription> findByEstDossierEcritValideFalse();
}
