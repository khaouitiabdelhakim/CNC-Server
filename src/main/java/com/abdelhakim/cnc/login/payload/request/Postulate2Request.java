package com.abdelhakim.cnc.login.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class Postulate2Request {

    private Integer anneeBac;


    private String typeBac;


    private String serieBac;


    private Float moyenne;


    private Long filiere;


    private String classe;

    private Long centreCpge;

    private String situation;

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

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }
}
