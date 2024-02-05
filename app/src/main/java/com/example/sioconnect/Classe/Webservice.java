package com.example.sioconnect.Classe;

public class Webservice {
    public static String url="http://192.168.0.106/~claude.boulay/ppe/Annuaire_PPE/public/webservice/";

    public String Addurl(String add){
        String newurl=url+add;
        return newurl;
    }
}
