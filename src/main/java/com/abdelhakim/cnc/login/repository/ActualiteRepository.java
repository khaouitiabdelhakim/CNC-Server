package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Actualite;
import com.abdelhakim.cnc.login.models.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualiteRepository extends JpaRepository<Actualite, Long> {

}
