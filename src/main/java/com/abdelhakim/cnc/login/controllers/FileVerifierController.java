package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

  @Autowired
  DossierOralRepository dossierOralRepository;

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

  @GetMapping("/students/invalid")
  public List<CompleteStudent> getInvalidStudents() {
    List<Student> students = studentRepository.findByEstProfilValideFalse();
    return getUsers(students);
  }

  private List<CompleteStudent> getUsers(List<Student> students) {
    List<CompleteStudent> users = new ArrayList<>();
    for (Student student : students) {
      Optional<User> user = userRepository.findById(student.getIdUser());
      if (user.isPresent()){
        CompleteStudent completeStudent =
                new CompleteStudent(student,user.get());
        users.add(completeStudent);
      }

    }
    return users;
  }

  @GetMapping("/students/complete/{id}")
  public ResponseEntity<CompleteStudent> getCompleteStudentFromFV(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);
    Optional<Student> student = studentRepository.findByIdUser(id);

    if (user.isPresent() && student.isPresent()) {
      CompleteStudent completeStudent = new CompleteStudent(student.get(), user.get());
      return ResponseEntity.ok(completeStudent);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/validate/{id}")
  public ResponseEntity<User> validateProfileStudent(@PathVariable Long id) {
    // Check if the user with the given ID exists and has the role "STUDENT"
    Optional<Student> studentOptional = studentRepository.findByIdUser(id);

    if (studentOptional.isPresent()) {
      Student student = studentOptional.get();

      // Update the est_profil_valide property to true for the student
      student.setEstProfilValide(true);

      // Save the updated user (student)
      studentRepository.save(student);

      // Create an Inscription with the given student ID
      Inscription inscription = new Inscription();
      inscription.setIdStudent(id);
      inscription.setEstDossierEcritValide(false);
      inscription.setEstDossierOralValide(false);

      // Save the Inscription to associate it with the generated ID
      Inscription savedInscription = inscriptionRepository.save(inscription);

      // Create DossierOral and associate them with the Inscription
      DossierOral dossierOral = new DossierOral();
      dossierOral.setIdInscription(savedInscription.getId());
      dossierOralRepository.save(dossierOral);

      // Create DossierEcrit and associate them with the Inscription
      DossierEcrit dossierEcrit = new DossierEcrit();
      dossierEcrit.setIdInscription(savedInscription.getId());
      dossierEcrit.setNom(student.getNom());
      dossierEcrit.setPrenom(student.getPrenom());
      dossierEcrit.setCin(student.getCin());
      dossierEcrit.setEmail(student.getEmailPersonnel());
      dossierEcritRepository.save(dossierEcrit);

      // Return a response indicating success or failure
      return ResponseEntity.ok().build();
    } else {
      // Handle the case where the user with the given ID is not found or is not a student
      return ResponseEntity.notFound().build();
    }
  }




  @PostMapping("/validate")
  public ResponseEntity<User> validateProfileStudent(@RequestParam String username) {
    // Find the user by the provided username
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      Long id = user.getId();

      // Check if the user with the given ID exists and has the role "STUDENT"
      Optional<Student> studentOptional = studentRepository.findByIdUser(id);

      if (studentOptional.isPresent()) {
        Student student = studentOptional.get();

        // Update the est_profil_valide property to true for the student
        student.setEstProfilValide(true);

        // Save the updated user (student)
        studentRepository.save(student);

        // Create an Inscription with the given student ID
        Inscription inscription = new Inscription();
        inscription.setIdStudent(id);
        inscription.setEstDossierEcritValide(false);
        inscription.setEstDossierOralValide(false);

        // Save the Inscription to associate it with the generated ID
        Inscription savedInscription = inscriptionRepository.save(inscription);

        // Create DossierOral and associate them with the Inscription
        DossierOral dossierOral = new DossierOral();
        dossierOral.setIdInscription(savedInscription.getId());
        dossierOralRepository.save(dossierOral);

        // Create DossierEcrit and associate them with the Inscription
        DossierEcrit dossierEcrit = new DossierEcrit();
        dossierEcrit.setIdInscription(savedInscription.getId());
        dossierEcritRepository.save(dossierEcrit);

        // Return a response indicating success or failure
        return ResponseEntity.ok().build();
      } else {
        // Handle the case where the user with the given ID is not found or is not a student
        return ResponseEntity.notFound().build();
      }
    } else {
      // Handle the case where the user with the given username is not found or is not a student
      return ResponseEntity.notFound().build();
    }
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
