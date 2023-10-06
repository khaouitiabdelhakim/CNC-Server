package com.abdelhakim.cnc.login.controllers;


import java.util.List;
import java.util.stream.Collectors;

import com.abdelhakim.cnc.login.exception.TokenRefreshException;
import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.payload.request.TokenRefreshRequest;
import com.abdelhakim.cnc.login.payload.response.JwtResponse;
import com.abdelhakim.cnc.login.payload.response.TokenRefreshResponse;
import com.abdelhakim.cnc.login.repository.*;
import com.abdelhakim.cnc.login.security.services.RefreshTokenService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abdelhakim.cnc.login.payload.request.LoginRequest;
import com.abdelhakim.cnc.login.payload.request.SignupRequest;
import com.abdelhakim.cnc.login.payload.response.MessageResponse;
import com.abdelhakim.cnc.login.security.jwt.JwtUtils;
import com.abdelhakim.cnc.login.security.services.UserDetailsImpl;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    StudentRepository studentRepository;

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


    @PostMapping("/sign-out")
  public ResponseEntity<?> logoutUser() {
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new MessageResponse("You've been signed out!"));
  }
}
