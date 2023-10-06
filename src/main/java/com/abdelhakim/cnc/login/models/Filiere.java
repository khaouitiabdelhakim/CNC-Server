package com.abdelhakim.cnc.login.models;

import jakarta.persistence.*;

@Entity
@Table(name = "filieres")
public class Filiere {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private EFiliere name;

  public Filiere() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EFiliere getName() {
    return name;
  }

  public void setName(EFiliere name) {
    this.name = name;
  }
}