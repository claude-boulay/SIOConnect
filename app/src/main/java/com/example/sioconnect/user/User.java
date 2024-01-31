package com.example.sioconnect.user;

public class User {
    private int id;
    private String identifiant;
    private String nom;
    private String prenom;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(int id, String identifiant, String nom, String prenom, String type) {
        this.id = id;
        this.identifiant = identifiant;
        this.nom = nom;
        this.prenom = prenom;
        this.type = type;
    }

    public User(){

    }
}
