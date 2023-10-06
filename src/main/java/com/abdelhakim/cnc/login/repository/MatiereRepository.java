package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Centre;
import com.abdelhakim.cnc.login.models.Matiere;
import com.abdelhakim.cnc.login.models.NomMatiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {

    Optional<Matiere> findByMatiere(NomMatiere nomMatiere);

}
