package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

@Entity
@Table(name = "matieres")
public class Matiere {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private NomMatiere matiere;

  @Column(nullable = false)
  private Integer coefficient;

  public Matiere() {
  }

  public Matiere(NomMatiere matiere, Integer coefficient) {
    this.matiere = matiere;
    this.coefficient = coefficient;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public NomMatiere getMatiere() {
    return matiere;
  }

  public void setMatiere(NomMatiere matiere) {
    this.matiere = matiere;
  }

  public Integer getCoefficient() {
    return coefficient;
  }

  public void setCoefficient(Integer coefficient) {
    this.coefficient = coefficient;
  }
}
