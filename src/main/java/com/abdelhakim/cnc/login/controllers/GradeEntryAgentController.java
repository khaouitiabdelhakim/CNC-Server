package com.abdelhakim.cnc.login.controllers;

import com.abdelhakim.cnc.login.models.*;
import com.abdelhakim.cnc.login.repository.*;
import com.abdelhakim.cnc.login.service.ClassementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//for Angular Client (withCredentials)
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
//@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('ROLE_GRADE_ENTRY_AGENT')")
@RestController
@RequestMapping("/api/grade-entry-agent")
public class GradeEntryAgentController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  InscriptionRepository inscriptionRepository;

  @Autowired
  DossierEcritRepository dossierEcritRepository;

  @Autowired
  MatiereRepository matiereRepository;

  @Autowired
  NoteRepository noteRepository;

  @GetMapping("/dashboard")
  public Map<String, Integer> adminAccess(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String username = userDetails.getUsername();
    List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
    return studentCounts();
  }

  private final ClassementService classementService;

  @Autowired
  public GradeEntryAgentController(ClassementService classementService) {
    this.classementService = classementService;
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

  @GetMapping("/students/{id}")
  public ResponseEntity<User> getStudent(@PathVariable Long id) {
    Optional<User> student = userRepository.findUserById(id);
    return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/students")
  public List<FullStudent> allStudents() {
    List<User> students = userRepository.findAllByRole(ERole.valueOf("STUDENT"));
    List<FullStudent> fullStudents = new ArrayList<>();
    for (User user : students) {
      Inscription inscription = inscriptionRepository.findByIdStudent(user.getId());
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscription.getId());

      FullStudent  fullStudent = new FullStudent(user,dossierEcrit);
      fullStudents.add(fullStudent);

    }
    return fullStudents;
  }

  public DossierEcrit getAlldetailsAboutStudent(Long id) {
    Optional<User> user = userRepository.findById(id);
    Inscription inscription = inscriptionRepository.findByIdStudent(user.get().getId());
    DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscription.getId());
    return dossierEcrit;
  }

  @GetMapping("/students-with-notes")
  public List<FullStudent> studentsWithNotes() {
    List<User> studentsWithNotes = userRepository.findStudentsWithNotes();
    List<FullStudent> fullStudents = new ArrayList<>();

    for (User user : studentsWithNotes) {
      Inscription inscription = inscriptionRepository.findByIdStudent(user.getId());
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscription.getId());

      FullStudent fullStudent = new FullStudent(user, dossierEcrit);
      fullStudents.add(fullStudent);
    }

    return fullStudents;
  }

  @GetMapping("/students-without-notes")
  public List<FullStudent> studentsWithoutNotes() {
    List<User> studentsWithoutNotes = userRepository.findStudentsWithoutNotes();
    List<FullStudent> fullStudentsWithoutNotes = new ArrayList<>();

    for (User user : studentsWithoutNotes) {
      Inscription inscription = inscriptionRepository.findByIdStudent(user.getId());
      DossierEcrit dossierEcrit = dossierEcritRepository.findByIdInscription(inscription.getId());

      FullStudent fullStudent = new FullStudent(user, dossierEcrit);
      fullStudentsWithoutNotes.add(fullStudent);
    }

    return fullStudentsWithoutNotes;
  }




  @GetMapping("/matieres")
  public List<Matiere> allSubjects() {
    return matiereRepository.findAll();
  }




  @PostMapping("/notes/{id}")
  public ResponseEntity<Map<String, String>> setNotes(
          @PathVariable Long id,
          @RequestBody Map<String, Float> notesData,
          Authentication authentication
  ) {
    try {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String gradeEntryAgentUsername = userDetails.getUsername();
      Optional<User> user = userRepository.findByUsername(gradeEntryAgentUsername);

      if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "User not found"));
      }

      Long gradeEntryAgentId = user.get().getId();

      for (Map.Entry<String, Float> entry : notesData.entrySet()) {
        String subject = entry.getKey();
        Float mark = entry.getValue();

        Optional<Matiere> matiereOptional = matiereRepository.findByMatiere(NomMatiere.valueOf(subject));

        if (matiereOptional.isPresent()) {
          Matiere matiere = matiereOptional.get();

          // Check if a note already exists for the same student, subject, and agent
          Optional<Note> existingNote = noteRepository.findByIdEtudiantAndIdMatiereAndIdAgent(id, matiere.getId(), gradeEntryAgentId);

          if (existingNote.isPresent()) {
            // Update the existing note
            Note noteToUpdate = existingNote.get();
            noteToUpdate.setNote(mark);
            noteRepository.save(noteToUpdate);
          } else {
            // Create a new note
            Note newNote = new Note();
            newNote.setIdEtudiant(id);
            newNote.setIdMatiere(matiere.getId());
            newNote.setNote(mark);
            newNote.setIdAgent(gradeEntryAgentId);
            noteRepository.save(newNote);
          }
        } else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Subject not found: " + subject));
        }
      }

      Map<String, String> responseMap = Collections.singletonMap("message", "Notes submitted successfully");
      return ResponseEntity.ok(responseMap);
    } catch (Exception e) {
      e.printStackTrace(); // Add this line to print the exception details
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error processing request: " + e.getMessage()));
    }
  }


  @PostMapping("/notes-from-cin/{cin}")
  public ResponseEntity<Map<String, String>> setNotesFromCIN(
          @PathVariable String cin,
          @RequestBody Map<String, Float> notesData,
          Authentication authentication
  ) {
    try {
      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      String gradeEntryAgentUsername = userDetails.getUsername();
      Optional<User> user = userRepository.findByUsername(gradeEntryAgentUsername);

      if (user.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "User not found"));
      }

      Long gradeEntryAgentId = user.get().getId();

      Optional<User> studentUserOptional = userRepository.findByUsername(cin);

      if (studentUserOptional.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Student not found"));
      }

      Long studentId = studentUserOptional.get().getId();

      for (Map.Entry<String, Float> entry : notesData.entrySet()) {
        String subject = entry.getKey();
        Float mark = entry.getValue();

        Optional<Matiere> matiereOptional = matiereRepository.findByMatiere(NomMatiere.valueOf(subject));

        if (matiereOptional.isPresent()) {
          Matiere matiere = matiereOptional.get();

          // Check if a note already exists for the same student, subject, and agent
          Optional<Note> existingNote = noteRepository.findByIdEtudiantAndIdMatiereAndIdAgent(studentId, matiere.getId(), gradeEntryAgentId);

          if (existingNote.isPresent()) {
            // Update the existing note
            Note noteToUpdate = existingNote.get();
            noteToUpdate.setNote(mark);
            noteRepository.save(noteToUpdate);
          } else {
            // Create a new note
            Note newNote = new Note();
            newNote.setIdEtudiant(studentId);
            newNote.setIdMatiere(matiere.getId());
            newNote.setNote(mark);
            newNote.setIdAgent(gradeEntryAgentId);
            noteRepository.save(newNote);
          }
        } else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", "Subject not found: " + subject));
        }
      }

      Map<String, String> responseMap = Collections.singletonMap("message", "Notes submitted successfully");
      return ResponseEntity.ok(responseMap);
    } catch (Exception e) {
      e.printStackTrace(); // Add this line to print the exception details
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error processing request: " + e.getMessage()));
    }
  }



  @GetMapping("/notes/{id}")
  public ResponseEntity<Map<String, Float>> getNotes(
          @PathVariable Long id
  ) {
    try {
      // Retrieve the student's notes with the specified ID
      List<Note> studentNotes = noteRepository.findByIdEtudiant(id);

      // Create a map to store subject names and their corresponding marks
      Map<String, Float> notesMap = new HashMap<>();

      // Fill the map with subject names and their respective marks
      for (Note note : studentNotes) {
        Matiere matiere = matiereRepository.findById(note.getIdMatiere()).orElse(null);
        if (matiere != null) {
          notesMap.put(matiere.getMatiere().toString(), note.getNote());
        }
      }

      return ResponseEntity.ok(notesMap);
    } catch (Exception e) {
      e.printStackTrace(); // Add this line to print exception details
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", (float) -1));
    }
  }




  //general methods
  public Map<String, Integer> studentCounts() {
    Map<String, Integer> counts = new HashMap<>();

    // Get the total number of students
    List<User> allStudents = userRepository.findAllByRole(ERole.valueOf("STUDENT"));
    int totalStudentCount = allStudents.size();
    counts.put("totalStudents", totalStudentCount);

    // Get the number of students with notes
    List<User> studentsWithNotes = userRepository.findStudentsWithNotes();
    int studentsWithNotesCount = studentsWithNotes.size();
    counts.put("studentsWithNotes", studentsWithNotesCount);

    return counts;
  }








}



