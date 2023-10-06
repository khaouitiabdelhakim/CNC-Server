package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

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

    public Student(Long idUser, Boolean estProfilValide) {
        this.idUser = idUser;
        this.estProfilValide = estProfilValide;
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
