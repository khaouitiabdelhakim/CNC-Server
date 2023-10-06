package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Admin;
import com.abdelhakim.cnc.login.models.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {

}
