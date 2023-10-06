package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.repository.DossierEcritRepository;
import com.abdelhakim.cnc.login.repository.InscriptionRepository;
import com.abdelhakim.cnc.login.repository.StudentRepository;
import com.abdelhakim.cnc.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('ROLE_FILE_VERIFIER')")
@RequestMapping("/api/file-verifier")
public class FileVerifierController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  StudentRepository studentRepository;

  @Autowired
  InscriptionRepository inscriptionRepository;

  @Autowired
  DossierEcritRepository dossierEcritRepository;

  @GetMapping("/dashboard")
  public String adminAccess(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();

    // Log username and authorities for debugging
    System.out.println("Username: " + username);
    System.out.println("Authorities: " + authorities);

    // Your admin-specific logic here
    return "Admin Board.";
  }


  @GetMapping("/complete-ecrit/{id}")
  public ResponseEntity<DossierEcrit> getCompleteFolder(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        return new ResponseEntity<>(dossierEcrit, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


  @GetMapping("/folders/invalid")
  public List<User> invalidFolders() {
    List<Inscription> inscriptions = inscriptionRepository.findByEstDossierEcritValideFalse();
    return getUsersByInscriptions(inscriptions);
  }

  @GetMapping("/folders/valid")
  public List<User> validFolders() {
    List<Inscription> inscriptions = inscriptionRepository.findByEstDossierEcritValideTrue();
    return getUsersByInscriptions(inscriptions);
  }

  private List<User> getUsersByInscriptions(List<Inscription> inscriptions) {
    return inscriptions.stream()
            .map(inscription -> userRepository.findById(inscription.getIdStudent()))
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
  }


  @GetMapping("/ecrit/{id}")
  public ResponseEntity<DossierEcrit> getStudentFolder(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {

        return new ResponseEntity<>(dossierEcrit, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/ecrit/validate/{id}")
  public ResponseEntity<Inscription> validateStudentFolder(@PathVariable Long id) {

    // Create an Inscription with the given student ID
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) { // Check if inscription is not null
      inscription.setEstDossierEcritValide(true);
      inscriptionRepository.save(inscription);

      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } else {
      // Handle the case where the Inscription is not found
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/oral/validate/{id}")
  public ResponseEntity<Inscription> validateStudentFolderTIPE(@PathVariable Long id) {

    // Create an Inscription with the given student ID
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) { // Check if inscription is not null
      inscription.setEstDossierOralValide(true);
      inscriptionRepository.save(inscription);

      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } else {
      // Handle the case where the Inscription is not found
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/ecrit/invalidate/{id}")
  public ResponseEntity<Inscription> invalidateStudentFolder(@PathVariable Long id) {

    // Create an Inscription with the given student ID
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) { // Check if inscription is not null
      inscription.setEstDossierEcritValide(false);
      inscriptionRepository.save(inscription);

      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } else {
      // Handle the case where the Inscription is not found
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/oral/invalidate/{id}")
  public ResponseEntity<Inscription> invalidateStudentFolderTIPE(@PathVariable Long id) {

    // Create an Inscription with the given student ID
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) { // Check if inscription is not null
      inscription.setEstDossierOralValide(false);
      inscriptionRepository.save(inscription);

      return new ResponseEntity<>(inscription, HttpStatus.OK);
    } else {
      // Handle the case where the Inscription is not found
      return ResponseEntity.notFound().build();
    }
  }




}
