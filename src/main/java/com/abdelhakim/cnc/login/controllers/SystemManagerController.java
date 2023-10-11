package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.service.FilesStorageService;
import com.abdelhakim.cnc.login.message.ResponseMessage;
import com.abdelhakim.cnc.login.models.Actualite;
import com.abdelhakim.cnc.login.models.FileInfo;
import com.abdelhakim.cnc.login.repository.ActualiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/system-manager")
public class SystemManagerController {



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


  @Autowired
  FilesStorageService storageService;

  @Autowired
  private ActualiteRepository actualiteRepository; // Assuming you have an ActualiteService for database operations

  @PostMapping("/uploadAndCreate")
  public ResponseEntity<ResponseMessage> uploadFilesAndCreateActualite(
          @RequestParam("cover") MultipartFile coverFile,
          @RequestParam("pdf") MultipartFile pdfFile,
          @RequestParam("title") String title) {

    String coverMessage = "";
    String pdfMessage = "";

    try {
      storageService.save(coverFile);
      coverMessage = "Uploaded the cover photo successfully: " + coverFile.getOriginalFilename();

      storageService.save(pdfFile);
      pdfMessage = "Uploaded the PDF file successfully: " + pdfFile.getOriginalFilename();

      // Create an Actualite object and save it to the database
      Actualite actualite = new Actualite();
      actualite.setTitre(title);
      actualite.setCoverUrl(coverFile.getOriginalFilename()); // You may want to store the actual URL
      actualite.setPdfUrl(pdfFile.getOriginalFilename()); // You may want to store the actual URL
      actualiteRepository.save(actualite); // Assuming you have a service to save Actualite objects

      // You can return a combined response message if needed
      String combinedMessage = "Upload successful:\n" + coverMessage + "\n" + pdfMessage;

      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(combinedMessage));
    } catch (Exception e) {
      String errorMessage = "Could not upload one or more files. Error: " + e.getMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(errorMessage));
    }
  }


  @GetMapping("/files")
  public ResponseEntity<List<FileInfo>> getListFiles() {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
              .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
  }

  @GetMapping("/actualities")
  public List<Actualite> getAllActualites() {
    return actualiteRepository.findAll();
  }


}
