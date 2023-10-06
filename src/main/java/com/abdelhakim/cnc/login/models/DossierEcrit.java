package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "dossiers_ecrits")
public class DossierEcrit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "id_inscription")
    private Long idInscription;

    @Column(name = "id_verificateur")
    private Long idVerifier;

    /*
    * nom: string;
    telephone: string;
    prenom: string;
    adresse: string;
    email: string;
    lienDeNaissance: string;
    cin: string;
    cne: string;
    genre: string; // You can use "Masculin" or "Féminin" as values
    dateDeNaissance: string; // Format: mm/dd/yyyy
    nationalite: string; // Assuming "Marocaine" as the default value*/

    @Column(nullable = true)
    private String nom;

    @Column(nullable = true)
    private String prenom;

    @Column(nullable = true)
    private String email;

    private String cin;


    private String cne;


    private String telephone;


    private String adresse;

    @Column(name = "lieu_de_naissance")
    private String lieuDeNaissance;

    @Column(name = "date_de_naissance")
    private Date dateDeNaissance;


    private String genre;


    private String nationalite;


    /*
    * anneeBac: number;
    serieBac: string;
    typeBac: string;
    moyenne: number;
    filiere: string;
    centreCPGE: string;
    classe: string;
    situation: string;*/
    @Column(name = "annee_bac")
    private Integer anneeBac;

    @Column(name = "type_bac")
    private String typeBac;

    @Column(name = "serie_bac")
    private String serieBac;


    private Float moyenne;

    @Column(name = "filiere_id")
    private Long filiere;


    private String classe;

    @Column(name = "centre_cpge_id")
    private Long centreCpge;

    private String situation;





    /*
    * // Properties for the 1ère année des CPGE
    annee1cpge: number;
    filiere1cpge: string;
    centreCpge1cpge: number;
    classe1cpge: string;

    // Properties for the 2ème année des CPGE
    annee2cpge: number;
    filiere2cpge: string;
    centreCpge2cpge: number;
    classe2cpge: string;

    // Properties for the section réservée aux 5/2 et 7/2
    annee3cpge: number;
    filiere3cpge: string;
    centreCpge3cpge: number;
    classe3cpge: string;
    *
    *
    * annee4cpge: number;
    filiere5cpge: string;
    centreCpge5cpge: number;
    classe5cpge: string;*/

    @Column(name = "annee_1_cpge")
    private Integer annee1cpge;

    @Column(name = "filiere_1_cpge_id")
    private Long filiere1cpge;

    @Column(name = "centre_1_cpge_id")
    private Long centreCpge1cpge;

    @Column(name = "classe_1_cpge")
    private String classe1cpge;

    @Column(name = "annee_2_cpge")
    private Integer annee2cpge;

    @Column(name = "filiere_2_cpge_id")
    private Long filiere2cpge;

    @Column(name = "centre_2_cpge_id")
    private Long centreCpge2cpge;

    @Column(name = "classe_2_cpge")
    private String classe2cpge;

    @Column(name = "annee_3_cpge")
    private Integer annee3cpge;

    @Column(name = "filiere_3_cpge_id")
    private Long filiere3cpge;

    @Column(name = "centre_3_cpge_id")
    private Long centreCpge3cpge;

    @Column(name = "classe_3_cpge")
    private String classe3cpge;

    @Column(name = "annee_4_cpge")
    private Integer annee4cpge;

    @Column(name = "filiere_4_cpge_id")
    private Long filiere4cpge;

    @Column(name = "centre_4_cpge_id")
    private Long centreCpge4cpge;

    @Column(name = "classe_4_cpge")
    private String classe4cpge;





    /*
    * // Properties for the 1ère Participation antérieure au CNC 2024
    annee1Participation: number;
    typeDe1candidature: string;

    // Properties for the 2ème Participation antérieure au CNC 2024
    annee2Participation: number;
    typeDe2candidature: string;*/



    @Column(name = "annee_1_participation")
    private Integer annee1Participation;

    @Column(name = "type_de_1_candidature")
    private String typeDe1candidature;

    @Column(name = "annee_2_participation")
    private Integer annee2Participation;

    @Column(name = "type_de_2_candidature")
    private String typeDe2candidature;







    /*
    * titreTIPE: string;
    motivationTIPE: string;
    ancrageTIPE: string;
    encadrantTIPE: string;*/
    @Column(name = "titre_tipe")
    private String titreTIPE;

    @Column(name = "motivation_tipe", columnDefinition = "TEXT")
    private String motivationTIPE;

    @Column(name = "ancrage_tipe", columnDefinition = "TEXT")
    private String ancrageTIPE;

    @Column(name = "encadrant_tipe")
    private String encadrantTIPE;

    @Column(name = "fiche_de_candidature")
    private String ficheDeCandidature;

    @Column(name = "cin_legalise")
    private String cinLegalise;

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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLieuDeNaissance() {
        return lieuDeNaissance;
    }

    public void setLieuDeNaissance(String lieuDeNaissance) {
        this.lieuDeNaissance = lieuDeNaissance;
    }

    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public Integer getAnneeBac() {
        return anneeBac;
    }

    public void setAnneeBac(Integer anneeBac) {
        this.anneeBac = anneeBac;
    }

    public String getTypeBac() {
        return typeBac;
    }

    public void setTypeBac(String typeBac) {
        this.typeBac = typeBac;
    }

    public String getSerieBac() {
        return serieBac;
    }

    public void setSerieBac(String serieBac) {
        this.serieBac = serieBac;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }



    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Integer getAnnee1cpge() {
        return annee1cpge;
    }

    public void setAnnee1cpge(Integer annee1cpge) {
        this.annee1cpge = annee1cpge;
    }



    public String getClasse1cpge() {
        return classe1cpge;
    }

    public void setClasse1cpge(String classe1cpge) {
        this.classe1cpge = classe1cpge;
    }

    public Integer getAnnee2cpge() {
        return annee2cpge;
    }

    public void setAnnee2cpge(Integer annee2cpge) {
        this.annee2cpge = annee2cpge;
    }


    public String getClasse2cpge() {
        return classe2cpge;
    }

    public void setClasse2cpge(String classe2cpge) {
        this.classe2cpge = classe2cpge;
    }

    public Integer getAnnee3cpge() {
        return annee3cpge;
    }

    public void setAnnee3cpge(Integer annee3cpge) {
        this.annee3cpge = annee3cpge;
    }



    public String getClasse3cpge() {
        return classe3cpge;
    }

    public void setClasse3cpge(String classe3cpge) {
        this.classe3cpge = classe3cpge;
    }

    public Integer getAnnee4cpge() {
        return annee4cpge;
    }

    public void setAnnee4cpge(Integer annee4cpge) {
        this.annee4cpge = annee4cpge;
    }

    public Long getFiliere() {
        return filiere;
    }

    public void setFiliere(Long filiere) {
        this.filiere = filiere;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Long getCentreCpge() {
        return centreCpge;
    }

    public void setCentreCpge(Long centreCpge) {
        this.centreCpge = centreCpge;
    }

    public Long getFiliere1cpge() {
        return filiere1cpge;
    }

    public void setFiliere1cpge(Long filiere1cpge) {
        this.filiere1cpge = filiere1cpge;
    }

    public Long getCentreCpge1cpge() {
        return centreCpge1cpge;
    }

    public void setCentreCpge1cpge(Long centreCpge1cpge) {
        this.centreCpge1cpge = centreCpge1cpge;
    }

    public Long getFiliere2cpge() {
        return filiere2cpge;
    }

    public void setFiliere2cpge(Long filiere2cpge) {
        this.filiere2cpge = filiere2cpge;
    }

    public Long getCentreCpge2cpge() {
        return centreCpge2cpge;
    }

    public void setCentreCpge2cpge(Long centreCpge2cpge) {
        this.centreCpge2cpge = centreCpge2cpge;
    }

    public Long getFiliere3cpge() {
        return filiere3cpge;
    }

    public void setFiliere3cpge(Long filiere3cpge) {
        this.filiere3cpge = filiere3cpge;
    }

    public Long getCentreCpge3cpge() {
        return centreCpge3cpge;
    }

    public void setCentreCpge3cpge(Long centreCpge3cpge) {
        this.centreCpge3cpge = centreCpge3cpge;
    }

    public Long getFiliere4cpge() {
        return filiere4cpge;
    }

    public void setFiliere4cpge(Long filiere4cpge) {
        this.filiere4cpge = filiere4cpge;
    }

    public Long getCentreCpge4cpge() {
        return centreCpge4cpge;
    }

    public void setCentreCpge4cpge(Long centreCpge4cpge) {
        this.centreCpge4cpge = centreCpge4cpge;
    }

    public String getClasse4cpge() {
        return classe4cpge;
    }

    public void setClasse4cpge(String classe4cpge) {
        this.classe4cpge = classe4cpge;
    }

    public Integer getAnnee1Participation() {
        return annee1Participation;
    }

    public void setAnnee1Participation(Integer annee1Participation) {
        this.annee1Participation = annee1Participation;
    }

    public String getTypeDe1candidature() {
        return typeDe1candidature;
    }

    public void setTypeDe1candidature(String typeDe1candidature) {
        this.typeDe1candidature = typeDe1candidature;
    }

    public Integer getAnnee2Participation() {
        return annee2Participation;
    }

    public void setAnnee2Participation(Integer annee2Participation) {
        this.annee2Participation = annee2Participation;
    }

    public String getTypeDe2candidature() {
        return typeDe2candidature;
    }

    public void setTypeDe2candidature(String typeDe2candidature) {
        this.typeDe2candidature = typeDe2candidature;
    }

    public String getTitreTIPE() {
        return titreTIPE;
    }

    public void setTitreTIPE(String titreTIPE) {
        this.titreTIPE = titreTIPE;
    }

    public String getMotivationTIPE() {
        return motivationTIPE;
    }

    public void setMotivationTIPE(String motivationTIPE) {
        this.motivationTIPE = motivationTIPE;
    }

    public String getAncrageTIPE() {
        return ancrageTIPE;
    }

    public void setAncrageTIPE(String ancrageTIPE) {
        this.ancrageTIPE = ancrageTIPE;
    }

    public String getEncadrantTIPE() {
        return encadrantTIPE;
    }

    public void setEncadrantTIPE(String encadrantTIPE) {
        this.encadrantTIPE = encadrantTIPE;
    }

    public String getFicheDeCandidature() {
        return ficheDeCandidature;
    }

    public void setFicheDeCandidature(String ficheDeCandidature) {
        this.ficheDeCandidature = ficheDeCandidature;
    }

    public String getCinLegalise() {
        return cinLegalise;
    }

    public void setCinLegalise(String cinLegalise) {
        this.cinLegalise = cinLegalise;
    }
}
