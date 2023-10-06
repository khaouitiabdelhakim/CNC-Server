package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    DossierEcritRepository dossierEcritRepository;

    @Autowired
    DossierOralRepository dossierOralRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

  @GetMapping("/dashboard")
  public UserDetails adminAccess(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails;
  }

    @GetMapping("/profile")
    public UserDetails adminProfile(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails;
    }

    @GetMapping("/students")
    public List<User> allStudents() {
        return
                userRepository.findAllByRole(ERole.valueOf("STUDENT"));
    }

    @GetMapping("/students/valid")
    public List<User> validStudents() {
        List<Student> students = studentRepository.findByEstProfilValideTrue();
        return getUsers(students);
    }


    @GetMapping("/students/invalid")
    public List<User> invalidStudents() {
        List<Student> students = studentRepository.findByEstProfilValideFalse();
        return getUsers(students);
    }

    private List<User> getUsers(List<Student> students) {
        List<User> users = new ArrayList<>();
        for (Student student : students) {
            Optional<User> user = userRepository.findById(student.getIdUser());
            user.ifPresent(users::add);
        }
        return users;
    }


    @GetMapping("/admins")
    public List<User> allAdmins() {
        return userRepository.findAllByRole(ERole.valueOf("ADMIN"));
    }

    @GetMapping("/file-verifiers")
    public List<User> allFilesVerifiers() {
        return
                userRepository.findAllByRole(ERole.valueOf("FILE_VERIFIER"));
    }

    @GetMapping("/system-managers")
    public List<User> allSystemManagers() {

        return
                userRepository.findAllByRole(ERole.valueOf("SYSTEM_MANAGER"));
    }

    @GetMapping("/grade-entry-agents")
    public List<User> allGradeEntryAgents() {

        return
                userRepository.findAllByRole(ERole.valueOf("GRADE_ENTRY_AGENT"));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<User> getStudent(@PathVariable Long id) {
        Optional<User> student = userRepository.findUserById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<User> getAdmin(@PathVariable Long id) {
        Optional<User> admin = userRepository.findUserById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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


}
