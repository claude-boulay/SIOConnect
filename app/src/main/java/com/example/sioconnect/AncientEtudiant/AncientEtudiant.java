package com.example.sioconnect.AncientEtudiant;

public class AncientEtudiant {

    int id;
    String nom;
    String prenom;
    int telephone;
    String mail;
    int promo;
    boolean travail;
    int id_categorie;

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

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getPromo() {
        return promo;
    }

    public void setPromo(int promo) {
        this.promo = promo;
    }

    public boolean isTravail() {
        return travail;
    }

    public void setTravail(boolean travail) {
        this.travail = travail;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }
    public AncientEtudiant(){

    }

    public AncientEtudiant(int id, String nom, String prenom, int telephone, String mail, int promo, boolean travail, int id_categorie) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
        this.promo = promo;
        this.travail = travail;
        this.id_categorie = id_categorie;
    }
}
