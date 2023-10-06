package com.abdelhakim.cnc.login.repository;

import com.abdelhakim.cnc.login.models.Matiere;
import com.abdelhakim.cnc.login.models.NomMatiere;
import com.abdelhakim.cnc.login.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {


    Optional<Note> findByIdEtudiantAndIdMatiereAndIdAgent(Long id, Long id1, Long gradeEntryAgentId);

    List<Note> findByIdEtudiant(Long id);

    @Query("SELECT DISTINCT n.idEtudiant FROM Note n")
    List<Long> findDistinctIdEtudiant();
}
