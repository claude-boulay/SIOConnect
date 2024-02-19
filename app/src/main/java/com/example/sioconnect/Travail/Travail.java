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
    private Date debut;
    private Date fin;


    public Travail(int id, Organisation org, AncientEtudiant etu, String profession, Date debut, Date fin) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String test=simpleDateFormat.format(fin);

        this.id = id;
        this.org = org;
        this.etu = etu;
        this.profession = profession;
        this.debut = new Date(simpleDateFormat.format(debut));
        this.fin = new Date(test);
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

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }
}
