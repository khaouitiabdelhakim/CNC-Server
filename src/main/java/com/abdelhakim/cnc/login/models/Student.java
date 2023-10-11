package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user", nullable = false)
    private Long idUser;
    @Column(name = "est_profil_valide", nullable = false)
    private Boolean estProfilValide;

    public Student() {
    }

    public Student(Long idUser, Boolean estProfilValide, String nom, String prenom, String emailPersonnel, String cin, String cne) {
        this.idUser = idUser;
        this.estProfilValide = estProfilValide;
        this.nom = nom;
        this.prenom = prenom;
        this.emailPersonnel = emailPersonnel;
        this.cin = cin;
    }

    @Column(nullable = true)
    private String nom;

    @Column(nullable = true)
    private String prenom;

    @Column(nullable = true)
    private String emailPersonnel;

    private String cin;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmailPersonnel() {
        return emailPersonnel;
    }

    public void setEmailPersonnel(String emailPersonnel) {
        this.emailPersonnel = emailPersonnel;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Boolean getEstProfilValide() {
        return estProfilValide;
    }

    public void setEstProfilValide(Boolean estProfilValide) {
        this.estProfilValide = estProfilValide;
    }

    public void setUser(User user) {
        this.idUser = user.getId();
    }
}
