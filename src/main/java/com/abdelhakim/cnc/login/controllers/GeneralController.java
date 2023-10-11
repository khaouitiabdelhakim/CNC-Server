package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.service.CINStorageService;
import com.abdelhakim.cnc.login.service.FilesStorageService;
import com.abdelhakim.cnc.login.models.Actualite;
import com.abdelhakim.cnc.login.models.Centre;
import com.abdelhakim.cnc.login.models.Filiere;
import com.abdelhakim.cnc.login.repository.ActualiteRepository;
import com.abdelhakim.cnc.login.repository.CentreRepository;
import com.abdelhakim.cnc.login.repository.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/general")
public class GeneralController {

  @Autowired
  FiliereRepository filiereRepository;

  @Autowired
  CentreRepository centreRepository;

  @GetMapping("/sectors")
  public List<Filiere> getSectors() {
    return filiereRepository.findAll();
  }

  @GetMapping("/centres")
  public List<Centre> getCentres() {
    return centreRepository.findAll();
  }

  @Autowired
  FilesStorageService storageService;

  @Autowired
  CINStorageService cinStorageService;


  @Autowired
  private ActualiteRepository actualiteRepository;

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @GetMapping("/cin/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> getCINFile(@PathVariable String filename) {
    Resource file = cinStorageService.load(filename);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }


  @GetMapping("/actualities")
  public List<Actualite> getAllActualites() {
    return actualiteRepository.findAll();
  }

}
