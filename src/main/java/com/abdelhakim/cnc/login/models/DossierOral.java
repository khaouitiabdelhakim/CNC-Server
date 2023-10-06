package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dossiers_oraux")
public class DossierOral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "id_inscription")
    private Long idInscription;


    @Column(name = "id_verificateur")
    private Long idVerifier;


    private String titre;

    @Column(columnDefinition = "TEXT")
    private String motivation;

    @Column( columnDefinition = "TEXT")
    private String ancrage;


    private String encadrant;

    @Column(name = "MCOT")
    private String MCOT;

    @Column(name = "DOT")
    private String DOT;

    public DossierOral() {
    }

    public DossierOral(Long idInscription, Long idVerifier, String titre, String motivation, String ancrage, String encadrant, String MCOT, String DOT) {
        this.idInscription = idInscription;
        this.idVerifier = idVerifier;
        this.titre = titre;
        this.motivation = motivation;
        this.ancrage = ancrage;
        this.encadrant = encadrant;
        this.MCOT = MCOT;
        this.DOT = DOT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(Long idInscription) {
        this.idInscription = idInscription;
    }

    public Long getIdVerifier() {
        return idVerifier;
    }

    public void setIdVerifier(Long idVerifier) {
        this.idVerifier = idVerifier;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getAncrage() {
        return ancrage;
    }

    public void setAncrage(String ancrage) {
        this.ancrage = ancrage;
    }

    public String getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(String encadrant) {
        this.encadrant = encadrant;
    }

    public String getMCOT() {
        return MCOT;
    }

    public void setMCOT(String MCOT) {
        this.MCOT = MCOT;
    }

    public String getDOT() {
        return DOT;
    }

    public void setDOT(String DOT) {
        this.DOT = DOT;
    }
}
