package com.abdelhakim.cnc.login.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class Postulate4Request {

    private Integer annee1Participation;


    private String typeDe1candidature;


    private Integer annee2Participation;


    private String typeDe2candidature;

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
}
