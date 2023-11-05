package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.message.ResponseMessage;
import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.payload.request.CompleteStudentRequest;
import com.abdelhakim.cnc.login.repository.*;
import com.abdelhakim.cnc.login.security.jwt.JwtUtils;
import com.abdelhakim.cnc.login.service.CINStorageService;
import com.abdelhakim.cnc.login.service.ClassementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    CINStorageService storageService;

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

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private final ClassementService classementService;

    @Autowired
    public AdminController(ClassementService classementService) {
        this.classementService = classementService;
    }


    @GetMapping("/dashboard")
  public UserDetails adminAccess(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails;
  }

    @GetMapping("/classement")
    public List<Classement> getStudentsOrderedBySum(@RequestParam(name = "ascending", defaultValue = "false") boolean ascending) {
        List<Classement> students =  classementService.calculateSumForStudentsOrderBySum(ascending);
        for (Classement classement: students) {
            DossierEcrit dossierEcrit = getAlldetailsAboutStudent(classement.getId());
            classement.setNom(dossierEcrit.getNom());
            classement.setPrenom(dossierEcrit.getPrenom());
            classement.setEmail(dossierEcrit.getEmail());
            classement.setCne(dossierEcrit.getCne());
        }

        return students;
    }

    public DossierEcrit getAlldetailsAboutStudent(Long id) {
        Optional<User> user = userRepository.findById(id);
        Inscription inscription = inscriptionRepository.findByIdStudent(user.get().getId());
        DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscription.getId());
        return dossierEcrit;
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
    public List<CompleteStudent> validStudents() {
        List<Student> students = studentRepository.findByEstProfilValideTrue();
        return getUsers(students);
    }


    @GetMapping("/students/invalid")
    public List<CompleteStudent> invalidStudents() {
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
    public ResponseEntity<CompleteStudent> getCompleteStudent(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        Optional<Student> student = studentRepository.findByIdUser(id);

        if (user.isPresent() && student.isPresent()) {
            CompleteStudent completeStudent = new CompleteStudent(student.get(), user.get());
            return ResponseEntity.ok(completeStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/insert")
    public ResponseEntity<ResponseMessage> addStudent(@Valid @RequestBody CompleteStudentRequest request) {

        try {
            if (userRepository.existsByUsername(request.getCin())) {
                String errorMessage = "CIN is already taken!";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
            }

            if (userRepository.existsByEmail(request.getEmail())) {
                String errorMessage = "Email is already in use";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
            }

            // Create a new user's account
            User user = new User(request.getCin(),
                    request.getNom(),
                    request.getPrenom(),
                    request.getEmail(),
                    encoder.encode(request.getPassword()));

            user.setRole(ERole.STUDENT);

            userRepository.save(user);

            Student student = new Student();
            student.setUser(user);
            student.setEstProfilValide(true);
            student.setNom(request.getNom());
            student.setEmailPersonnel(request.getEmail());
            student.setPrenom(request.getPrenom());
            student.setCin("not yet available");

            // Add logging
            System.out.println("Before saving student");
            studentRepository.save(student);
            System.out.println("After saving student");

            // Call adminValidateStudentAndCreateFolders with the ID of the created user
            adminValidateStudentAndCreateFolders(user);

            String successMessage = "User registered successfully!";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(successMessage));

        } catch (Exception e) {
            String errorMessage = "Could not register student: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(errorMessage));
        }
    }


    public void adminValidateStudentAndCreateFolders(User user) {
        // Check if the user with the given ID exists and has the role "STUDENT"
        Optional<Student> studentOptional = studentRepository.findByIdUser(user.getId());

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            // Update the est_profil_valide property to true for the student
            student.setEstProfilValide(true);

            // Save the updated user (student)
            studentRepository.save(student);

            // Create an Inscription with the given student ID
            Inscription inscription = new Inscription();
            inscription.setIdStudent(user.getId());
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
            dossierEcrit.setCin(user.getUsername());
            dossierEcrit.setEmail(student.getEmailPersonnel());
            dossierEcritRepository.save(dossierEcrit);

            // Return a response indicating success or failure
            ResponseEntity.ok().build();
        } else {
            // Handle the case where the user with the given ID is not found or is not a student
            ResponseEntity.notFound().build();
        }
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









}
