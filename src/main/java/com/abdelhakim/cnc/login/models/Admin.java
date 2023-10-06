package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    public Admin() {
    }

    public Admin(Long idUser) {
        this.idUser = idUser;
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

    public void setUser(User user) {
        this.idUser = user.getId();
    }
}
