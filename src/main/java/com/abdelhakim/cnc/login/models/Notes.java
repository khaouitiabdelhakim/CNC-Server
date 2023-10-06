package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;


public class Notes {


    float MATHEMATIQUES_I,
            MATHEMATIQUES_II,
            PHYSIQUE_I,
            PHYSIQUE_II,
            CHIMIE,
            INFORMATIQUE,
            CULTURE_ARABE_ET_TRADUCTION,
            FRANCAIS,
            ANGLAIS,
            SCIENCES_INDUSTRIELLES,
            TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES;

    public float getMATHEMATIQUES_I() {
        return MATHEMATIQUES_I;
    }

    public void setMATHEMATIQUES_I(float MATHEMATIQUES_I) {
        this.MATHEMATIQUES_I = MATHEMATIQUES_I;
    }

    public float getMATHEMATIQUES_II() {
        return MATHEMATIQUES_II;
    }

    public void setMATHEMATIQUES_II(float MATHEMATIQUES_II) {
        this.MATHEMATIQUES_II = MATHEMATIQUES_II;
    }

    public float getPHYSIQUE_I() {
        return PHYSIQUE_I;
    }

    public void setPHYSIQUE_I(float PHYSIQUE_I) {
        this.PHYSIQUE_I = PHYSIQUE_I;
    }

    public float getPHYSIQUE_II() {
        return PHYSIQUE_II;
    }

    public void setPHYSIQUE_II(float PHYSIQUE_II) {
        this.PHYSIQUE_II = PHYSIQUE_II;
    }

    public float getCHIMIE() {
        return CHIMIE;
    }

    public void setCHIMIE(float CHIMIE) {
        this.CHIMIE = CHIMIE;
    }

    public float getINFORMATIQUE() {
        return INFORMATIQUE;
    }

    public void setINFORMATIQUE(float INFORMATIQUE) {
        this.INFORMATIQUE = INFORMATIQUE;
    }

    public float getCULTURE_ARABE_ET_TRADUCTION() {
        return CULTURE_ARABE_ET_TRADUCTION;
    }

    public void setCULTURE_ARABE_ET_TRADUCTION(float CULTURE_ARABE_ET_TRADUCTION) {
        this.CULTURE_ARABE_ET_TRADUCTION = CULTURE_ARABE_ET_TRADUCTION;
    }

    public float getFRANCAIS() {
        return FRANCAIS;
    }

    public void setFRANCAIS(float FRANCAIS) {
        this.FRANCAIS = FRANCAIS;
    }

    public float getANGLAIS() {
        return ANGLAIS;
    }

    public void setANGLAIS(float ANGLAIS) {
        this.ANGLAIS = ANGLAIS;
    }

    public float getSCIENCES_INDUSTRIELLES() {
        return SCIENCES_INDUSTRIELLES;
    }

    public void setSCIENCES_INDUSTRIELLES(float SCIENCES_INDUSTRIELLES) {
        this.SCIENCES_INDUSTRIELLES = SCIENCES_INDUSTRIELLES;
    }

    public float getTECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES() {
        return TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES;
    }

    public void setTECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES(float TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES) {
        this.TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES = TECHNOLOGIES_ET_SCIENCES_INDUSTRIELLES;
    }
}
