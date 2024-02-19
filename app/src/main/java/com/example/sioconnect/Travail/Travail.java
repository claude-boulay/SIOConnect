package com.example.sioconnect.Travail;

import com.example.sioconnect.AncientEtudiant.AncientEtudiant;
import com.example.sioconnect.Organisation.Organisation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Travail {
    private int id;
    private Organisation org;
    private AncientEtudiant etu;
    private String profession;
    private String debut;
    private String fin;


    public Travail( Organisation org,  String profession, String debut, String fin) {
        //SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        //String test=simpleDateFormat.format(fin);


        this.org = org;

        this.profession = profession;
        this.debut =debut;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Organisation getOrg() {
        return org;
    }

    public void setOrg(Organisation org) {
        this.org = org;
    }

    public AncientEtudiant getEtu() {
        return etu;
    }

    public void setEtu(AncientEtudiant etu) {
        this.etu = etu;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
