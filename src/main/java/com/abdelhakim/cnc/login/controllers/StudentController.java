package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.payload.request.*;
import com.abdelhakim.cnc.login.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/student")
public class StudentController {

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
  public ResponseEntity<CompleteStudent> studentAccess(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    Optional<User> user = userRepository.findByUsername(username);

    if (user.isPresent()) {

      Optional<Student> student = studentRepository.findByIdUser(user.get().getId());
      if (student.isPresent()) {

        CompleteStudent completeStudent = new CompleteStudent(student.get(), user.get());
        return ResponseEntity.ok(completeStudent);
      }
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/profile-status")
  public Boolean profileStatus(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();

    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      Optional<Student> student = studentRepository.findByIdUser(user.get().getId());
      if (student.isPresent()) {
        return  student.get().getEstProfilValide();
      }
    }
    // Your admin-specific logic here
    return false;
  }

  @PostMapping("/postulate-1/{id}")
  public Boolean postulateOne(@PathVariable Long id, @Valid @RequestBody Postulate1Request postulate1Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId(); // Assuming getId() returns the ID of the inscription

      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      // Now you can work with the 'dossierEcrit' object if it's not null
      if (dossierEcrit != null) {
        // Update the properties of dossierEcrit with values from postulate1Request
        dossierEcrit.setCin(postulate1Request.getCin());
        dossierEcrit.setEmail(postulate1Request.getEmail());
        dossierEcrit.setNom(postulate1Request.getNom());
        dossierEcrit.setPrenom(postulate1Request.getPrenom());
        dossierEcrit.setCne(postulate1Request.getCne());
        dossierEcrit.setTelephone(postulate1Request.getTelephone());
        dossierEcrit.setAdresse(postulate1Request.getAdresse());
        dossierEcrit.setLieuDeNaissance(postulate1Request.getLieuDeNaissance());
        dossierEcrit.setDateDeNaissance(postulate1Request.getDateDeNaissance());
        dossierEcrit.setGenre(postulate1Request.getGenre());
        dossierEcrit.setNationalite(postulate1Request.getNationalite());

        // Save the updated dossierEcrit
        dossierEcritRepository.save(dossierEcrit);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }


  @PostMapping("/postulate-2/{id}")
  public Boolean postulateTwo(@PathVariable Long id, @Valid @RequestBody Postulate2Request postulate2Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();

      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Update the properties of dossierEcrit with values from postulate2Request
        dossierEcrit.setAnneeBac(postulate2Request.getAnneeBac());
        dossierEcrit.setTypeBac(postulate2Request.getTypeBac());
        dossierEcrit.setSerieBac(postulate2Request.getSerieBac());
        dossierEcrit.setMoyenne(postulate2Request.getMoyenne());
        dossierEcrit.setFiliere(postulate2Request.getFiliere());
        dossierEcrit.setClasse(postulate2Request.getClasse());
        dossierEcrit.setCentreCpge(postulate2Request.getCentreCpge());
        dossierEcrit.setSituation(postulate2Request.getSituation());

        // Save the updated dossierEcrit
        dossierEcritRepository.save(dossierEcrit);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }


  @PostMapping("/postulate-3/{id}")
  public Boolean postulateThree(@PathVariable Long id, @Valid @RequestBody Postulate3Request postulate3Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();

      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Update the properties of dossierEcrit with values from postulate3Request
        dossierEcrit.setAnnee1cpge(postulate3Request.getAnnee1cpge());
        dossierEcrit.setFiliere1cpge(postulate3Request.getFiliere1cpge());
        dossierEcrit.setCentreCpge1cpge(postulate3Request.getCentreCpge1cpge());
        dossierEcrit.setClasse1cpge(postulate3Request.getClasse1cpge());
        dossierEcrit.setAnnee2cpge(postulate3Request.getAnnee2cpge());
        dossierEcrit.setFiliere2cpge(postulate3Request.getFiliere2cpge());
        dossierEcrit.setCentreCpge2cpge(postulate3Request.getCentreCpge2cpge());
        dossierEcrit.setClasse2cpge(postulate3Request.getClasse2cpge());
        dossierEcrit.setAnnee3cpge(postulate3Request.getAnnee3cpge());
        dossierEcrit.setFiliere3cpge(postulate3Request.getFiliere3cpge());
        dossierEcrit.setCentreCpge3cpge(postulate3Request.getCentreCpge3cpge());
        dossierEcrit.setClasse3cpge(postulate3Request.getClasse3cpge());
        dossierEcrit.setAnnee4cpge(postulate3Request.getAnnee4cpge());
        dossierEcrit.setFiliere4cpge(postulate3Request.getFiliere4cpge());
        dossierEcrit.setCentreCpge4cpge(postulate3Request.getCentreCpge4cpge());
        dossierEcrit.setClasse4cpge(postulate3Request.getClasse4cpge());

        // Save the updated dossierEcrit
        dossierEcritRepository.save(dossierEcrit);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }


  @PostMapping("/postulate-4/{id}")
  public Boolean postulateFour(@PathVariable Long id, @Valid @RequestBody Postulate4Request postulate4Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();

      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Update the properties of dossierEcrit with values from postulate4Request
        dossierEcrit.setAnnee1Participation(postulate4Request.getAnnee1Participation());
        dossierEcrit.setTypeDe1candidature(postulate4Request.getTypeDe1candidature());
        dossierEcrit.setAnnee2Participation(postulate4Request.getAnnee2Participation());
        dossierEcrit.setTypeDe2candidature(postulate4Request.getTypeDe2candidature());

        // Save the updated dossierEcrit
        dossierEcritRepository.save(dossierEcrit);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }


  @PostMapping("/postulate-5/{id}")
  public Boolean postulateFive(@PathVariable Long id, @Valid @RequestBody Postulate5Request postulate5Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();

      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Update the properties of dossierEcrit with values from postulate5Request
        dossierEcrit.setTitreTIPE(postulate5Request.getTitreTIPE());
        dossierEcrit.setMotivationTIPE(postulate5Request.getMotivationTIPE());
        dossierEcrit.setAncrageTIPE(postulate5Request.getAncrageTIPE());
        dossierEcrit.setEncadrantTIPE(postulate5Request.getEncadrantTIPE());

        // Save the updated dossierEcrit
        dossierEcritRepository.save(dossierEcrit);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }




  @PostMapping("/postulate-oral-1/{id}")
  public Boolean postulateOralOne(@PathVariable Long id, @Valid @RequestBody Postulate5Request postulate5Request) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();

      DossierOral dossierOral = dossierOralRepository.findByIdInscription(inscriptionId);

      if (dossierOral != null) {
        // Update the properties of dossierEcrit with values from postulate5Request
        dossierOral.setTitre(postulate5Request.getTitreTIPE());
        dossierOral.setMotivation(postulate5Request.getMotivationTIPE());
        dossierOral.setAncrage(postulate5Request.getAncrageTIPE());
        dossierOral.setEncadrant(postulate5Request.getEncadrantTIPE());

        // Save the updated dossierEcrit
        dossierOralRepository.save(dossierOral);

        return true; // Return true if the update was successful
      }
    }

    return false; // Return false if any of the data is not found or if there's an issue
  }

  @GetMapping("/postulate-oral-1/{id}")
  public ResponseEntity<Postulate5Request> getPostulateOralOne(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierOral dossierOral = dossierOralRepository.findByIdInscription(inscriptionId);

      if (dossierOral != null) {
        // Create a Postulate5Request object and populate it with data from dossierEcrit
        Postulate5Request postulate5Request = new Postulate5Request();
        postulate5Request.setTitreTIPE(dossierOral.getTitre());
        postulate5Request.setMotivationTIPE(dossierOral.getMotivation());
        postulate5Request.setAncrageTIPE(dossierOral.getAncrage());
        postulate5Request.setEncadrantTIPE(dossierOral.getEncadrant());

        return new ResponseEntity<>(postulate5Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }





  /*the get of these POST methods*/

  @GetMapping("/postulate-1/{id}")
  public ResponseEntity<Postulate1Request> getPostulateOne(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Create a Postulate1Request object and populate it with data from dossierEcrit
        Postulate1Request postulate1Request = new Postulate1Request();
        postulate1Request.setCin(dossierEcrit.getCin());
        postulate1Request.setEmail(dossierEcrit.getEmail());
        postulate1Request.setNom(dossierEcrit.getNom());
        postulate1Request.setPrenom(dossierEcrit.getPrenom());
        postulate1Request.setCne(dossierEcrit.getCne());
        postulate1Request.setTelephone(dossierEcrit.getTelephone());
        postulate1Request.setAdresse(dossierEcrit.getAdresse());
        postulate1Request.setLieuDeNaissance(dossierEcrit.getLieuDeNaissance());
        postulate1Request.setDateDeNaissance(dossierEcrit.getDateDeNaissance());
        postulate1Request.setGenre(dossierEcrit.getGenre());
        postulate1Request.setNationalite(dossierEcrit.getNationalite());

        return new ResponseEntity<>(postulate1Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/postulate-2/{id}")
  public ResponseEntity<Postulate2Request> getPostulateTwo(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Create a Postulate2Request object and populate it with data from dossierEcrit
        Postulate2Request postulate2Request = new Postulate2Request();
        postulate2Request.setAnneeBac(dossierEcrit.getAnneeBac());
        postulate2Request.setTypeBac(dossierEcrit.getTypeBac());
        postulate2Request.setSerieBac(dossierEcrit.getSerieBac());
        postulate2Request.setMoyenne(dossierEcrit.getMoyenne());
        postulate2Request.setFiliere(dossierEcrit.getFiliere());
        postulate2Request.setClasse(dossierEcrit.getClasse());
        postulate2Request.setCentreCpge(dossierEcrit.getCentreCpge());
        postulate2Request.setSituation(dossierEcrit.getSituation());

        return new ResponseEntity<>(postulate2Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/postulate-3/{id}")
  public ResponseEntity<Postulate3Request> getPostulateThree(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Create a Postulate3Request object and populate it with data from dossierEcrit
        Postulate3Request postulate3Request = new Postulate3Request();
        postulate3Request.setAnnee1cpge(dossierEcrit.getAnnee1cpge());
        postulate3Request.setFiliere1cpge(dossierEcrit.getFiliere1cpge());
        postulate3Request.setCentreCpge1cpge(dossierEcrit.getCentreCpge1cpge());
        postulate3Request.setClasse1cpge(dossierEcrit.getClasse1cpge());
        postulate3Request.setAnnee2cpge(dossierEcrit.getAnnee2cpge());
        postulate3Request.setFiliere2cpge(dossierEcrit.getFiliere2cpge());
        postulate3Request.setCentreCpge2cpge(dossierEcrit.getCentreCpge2cpge());
        postulate3Request.setClasse2cpge(dossierEcrit.getClasse2cpge());
        postulate3Request.setAnnee3cpge(dossierEcrit.getAnnee3cpge());
        postulate3Request.setFiliere3cpge(dossierEcrit.getFiliere3cpge());
        postulate3Request.setCentreCpge3cpge(dossierEcrit.getCentreCpge3cpge());
        postulate3Request.setClasse3cpge(dossierEcrit.getClasse3cpge());
        postulate3Request.setAnnee4cpge(dossierEcrit.getAnnee4cpge());
        postulate3Request.setFiliere4cpge(dossierEcrit.getFiliere4cpge());
        postulate3Request.setCentreCpge4cpge(dossierEcrit.getCentreCpge4cpge());
        postulate3Request.setClasse4cpge(dossierEcrit.getClasse4cpge());

        return new ResponseEntity<>(postulate3Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/postulate-4/{id}")
  public ResponseEntity<Postulate4Request> getPostulateFour(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Create a Postulate4Request object and populate it with data from dossierEcrit
        Postulate4Request postulate4Request = new Postulate4Request();
        postulate4Request.setAnnee1Participation(dossierEcrit.getAnnee1Participation());
        postulate4Request.setTypeDe1candidature(dossierEcrit.getTypeDe1candidature());
        postulate4Request.setAnnee2Participation(dossierEcrit.getAnnee2Participation());
        postulate4Request.setTypeDe2candidature(dossierEcrit.getTypeDe2candidature());

        return new ResponseEntity<>(postulate4Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/postulate-5/{id}")
  public ResponseEntity<Postulate5Request> getPostulateFive(@PathVariable Long id) {
    Inscription inscription = inscriptionRepository.findByIdStudent(id);

    if (inscription != null) {
      Long inscriptionId = inscription.getId();
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscriptionId);

      if (dossierEcrit != null) {
        // Create a Postulate5Request object and populate it with data from dossierEcrit
        Postulate5Request postulate5Request = new Postulate5Request();
        postulate5Request.setTitreTIPE(dossierEcrit.getTitreTIPE());
        postulate5Request.setMotivationTIPE(dossierEcrit.getMotivationTIPE());
        postulate5Request.setAncrageTIPE(dossierEcrit.getAncrageTIPE());
        postulate5Request.setEncadrantTIPE(dossierEcrit.getEncadrantTIPE());

        return new ResponseEntity<>(postulate5Request, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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









}
