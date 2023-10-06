package com.abdelhakim.cnc.login.models;


import jakarta.persistence.*;

@Entity
@Table(name = "centres")
public class Centre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private CentreType type;

    @Column(nullable = false)
    private Integer nbreSalles;

    public Centre() {
    }

    public Centre(String nom, String description, CentreType type, Integer nbreSalles) {
        this.nom = nom;
        this.description = description;
        this.type = type;
        this.nbreSalles = nbreSalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CentreType getType() {
        return type;
    }

    public void setType(CentreType type) {
        this.type = type;
    }

    public Integer getNbreSalles() {
        return nbreSalles;
    }

    public void setNbreSalles(Integer nbreSalles) {
        this.nbreSalles = nbreSalles;
    }
}
