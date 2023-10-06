package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.DossierEcrit;
import com.abdelhakim.cnc.login.models.Inscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierEcritRepository extends JpaRepository<DossierEcrit, Long> {


    DossierEcrit findByIdInscription(Long inscriptionId);
}
