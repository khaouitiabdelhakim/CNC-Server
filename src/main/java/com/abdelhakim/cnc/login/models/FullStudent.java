package com.abdelhakim.cnc.login.models;


public class FullStudent {
    private Long id;

    private String cin;
    private String nom;
    private String prenom;

    private String filiere;

    private String centre;

    public FullStudent(User user, DossierEcrit dossierEcrit) {
        this.id = user.getId();
        this.cin = user.getUsername();
        this.nom = dossierEcrit.getNom();
        this.prenom = dossierEcrit.getPrenom();
        this.filiere = String.valueOf(dossierEcrit.getFiliere());
        this.centre = String.valueOf(dossierEcrit.getCentreCpge());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cni) {
        this.cin = cni;
    }

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

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }
}
