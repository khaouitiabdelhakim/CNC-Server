package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.DossierEcrit;
import com.abdelhakim.cnc.login.models.DossierOral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierOralRepository extends JpaRepository<DossierOral, Long> {

    DossierOral findByIdInscription(Long inscriptionId);
}
