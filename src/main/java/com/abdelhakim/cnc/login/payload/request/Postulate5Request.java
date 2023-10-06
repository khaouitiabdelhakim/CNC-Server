package com.abdelhakim.cnc.login.payload.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class Postulate5Request {


    private String titreTIPE;


    private String motivationTIPE;


    private String ancrageTIPE;


    private String encadrantTIPE;

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
}
