package com.abdelhakim.cnc.login.models;

import jakarta.persistence.Column;

public class Classement {
    private  int classement;
    private Long id;
    private double total;

    public int getClassement() {
        return classement;
    }


    private String nom;


    private String prenom;


    private String email;

    private String cne;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public void setClassement(int classement) {
        this.classement = classement;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
