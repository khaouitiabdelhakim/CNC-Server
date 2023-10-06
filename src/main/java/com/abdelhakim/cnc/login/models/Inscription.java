package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;
@Entity
@Table(name = "inscriptions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id_student")
        })
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_student")
    private Long idStudent;

    @Column(name = "est_dossier_ecrit_valide")
    private Boolean estDossierEcritValide;

    @Column(name = "est_dossier_oral_valide")
    private Boolean estDossierOralValide;

    public Inscription() {
    }

    public Inscription(Long idStudent, Boolean estDossierEcritValide, Boolean estDossierOralValide) {
        this.idStudent = idStudent;
        this.estDossierEcritValide = estDossierEcritValide;
        this.estDossierOralValide = estDossierOralValide;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public Boolean getEstDossierEcritValide() {
        return estDossierEcritValide;
    }

    public void setEstDossierEcritValide(Boolean estDossierEcritValide) {
        this.estDossierEcritValide = estDossierEcritValide;
    }

    public Boolean getEstDossierOralValide() {
        return estDossierOralValide;
    }

    public void setEstDossierOralValide(Boolean estDossierOralValide) {
        this.estDossierOralValide = estDossierOralValide;
    }
}
