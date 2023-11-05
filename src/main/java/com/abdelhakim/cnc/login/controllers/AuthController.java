package com.abdelhakim.cnc.login.controllers;


import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.abdelhakim.cnc.login.exception.TokenRefreshException;
import com.abdelhakim.cnc.login.message.ResponseMessage;
import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.payload.request.CompleteStudentRequest;
import com.abdelhakim.cnc.login.payload.request.TokenRefreshRequest;
import com.abdelhakim.cnc.login.payload.response.JwtResponse;
import com.abdelhakim.cnc.login.payload.response.TokenRefreshResponse;
import com.abdelhakim.cnc.login.repository.*;
import com.abdelhakim.cnc.login.security.services.RefreshTokenService;
import com.abdelhakim.cnc.login.service.CINStorageService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.abdelhakim.cnc.login.payload.request.LoginRequest;
import com.abdelhakim.cnc.login.payload.request.SignupRequest;
import com.abdelhakim.cnc.login.payload.response.MessageResponse;
import com.abdelhakim.cnc.login.security.jwt.JwtUtils;
import com.abdelhakim.cnc.login.security.services.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    CINStorageService storageService;
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    InscriptionRepository inscriptionRepository;

    @Autowired
    DossierEcritRepository dossierEcritRepository;

    @Autowired
    DossierOralRepository dossierOralRepository;



    @Autowired
    FileVerifierRepository fileVerifierRepository;

    @Autowired
    SystemManagerRepository systemManagerRepository;

    @Autowired
    GradeEntryAgentRepository gradeEntryAgentRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/refreshment")
    public ResponseEntity<?> refreshment(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }












    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        try {
            if (userRepository.existsByUsername(signUpRequest.getCin())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: CIN is already taken!"));
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            // Create a new user's account
            User user = new User(signUpRequest.getCin(),
                    signUpRequest.getNom(),
                    signUpRequest.getPrenom(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            ERole role = signUpRequest.getRole();
            user.setRole(role);

            // Save the user to the users table
            userRepository.save(user);

            // Depending on the user's role, add additional data to the corresponding table
            switch (role) {
                case STUDENT -> {
                    Student student = new Student();
                    student.setUser(user);
                    student.setEstProfilValide(false);
                    // Set additional student-specific attributes
                    studentRepository.save(student);
                }
                case ADMIN -> {
                    Admin admin = new Admin();
                    admin.setUser(user);
                    // Set additional student-specific attributes
                    adminRepository.save(admin);
                }
                case FILE_VERIFIER -> {
                    FileVerifier fileVerifier = new FileVerifier();
                    fileVerifier.setUser(user);
                    // Set additional student-specific attributes
                    fileVerifierRepository.save(fileVerifier);
                }
                case SYSTEM_MANAGER -> {
                    SystemManager systemManager = new SystemManager();
                    systemManager.setUser(user);
                    // Set additional student-specific attributes
                    systemManagerRepository.save(systemManager);
                }
                case GRADE_ENTRY_AGENT -> {
                    GradeEntryAgent gradeEntryAgent = new GradeEntryAgent();
                    gradeEntryAgent.setUser(user);
                    // Set additional student-specific attributes
                    gradeEntryAgentRepository.save(gradeEntryAgent);
                }
                default -> {
                    // Handle other roles or provide a default behavior
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("no role specified error."));
                }
            }

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred during registration."));
        }
    }









    @Transactional
    @PostMapping("/inscrire")
    public ResponseEntity<ResponseMessage> inscrireStudent(
            @RequestParam("carte") MultipartFile carte,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("cin") String cin,
            @RequestParam("password") String password) {


        String carteMessage = "";

        try {
            String carteFileName = "cin_"+ cin + "." + StringUtils.getFilenameExtension(carte.getOriginalFilename());
            storageService.saveWithNewName(carte, carteFileName);

            carteMessage = "Uploaded the cover photo successfully: " + carte.getOriginalFilename();


            try {
                if (userRepository.existsByUsername(cin)) {
                    String errorMessage = "CIN is already taken!";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
                }

                if (userRepository.existsByEmail(email)) {
                    String errorMessage = "Email is already in use";
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(errorMessage));
                }

                // Create a new user's account
                User user = new User(cin,
                        nom,
                        prenom,
                        email,
                        encoder.encode(password));


                user.setRole(ERole.valueOf("STUDENT"));

                userRepository.save(user);


                Student student = new Student();
                student.setUser(user);
                student.setEstProfilValide(false);
                student.setNom(nom);
                student.setEmailPersonnel(email);
                student.setPrenom(prenom);
                student.setCin(carteFileName);

                // Add logging
                System.out.println("Before saving student"); // Add this line
                studentRepository.save(student);
                System.out.println("After saving student"); // Add this line

                String errorMessage = "User registered successfully!";
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(errorMessage));

            } catch (Exception e) {
                String errorMessage = "Could not register student: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(errorMessage));
            }
        } catch (Exception e) {
            String errorMessage = "Could not upload one or more files: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(errorMessage));
        }
    }



    @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
