package com.example.sioconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Adapter.accueilAdapter;
import com.example.sioconnect.AncientEtudiant.AncientEtudiant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Accueil extends AppCompatActivity {
    RequestQueue Filerequete;
    String Token;
    RecyclerView rvAccueil;

    ArrayList<AncientEtudiant> etudiants= new ArrayList<AncientEtudiant>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_accueil);
        Intent accueil=getIntent();
        Token=accueil.getStringExtra("token");
        rvAccueil=(RecyclerView)findViewById(R.id.accueil);
       // onCreateOptionsMenu(findViewById(R.id.menu));
        //getActionBar().show();
        Filerequete= Volley.newRequestQueue(this);

        RequestAccueil();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_base) {
            Toast.makeText(this, "Other menu selected", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.menu_settings) {
            Toast.makeText(this, "Settings menu selected", Toast.LENGTH_LONG).show();
            return true;
        } return super.onOptionsItemSelected(item);
    }

    public void RequestAccueil(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET,"http://192.168.0.106/~claude.boulay/ppe/Annuaire_PPE/public/webservice/getAncienEtudiants",this::processAccueil,this::gereErreur){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }
        };
        Filerequete.add(stringRequest);
    }

    public void processAccueil(String response){
        try {
            JSONArray ja=new JSONArray(response);
            int taille=ja.length();
            for (int i=0;i<taille;i++){
                JSONObject jo=ja.getJSONObject(i);
                Boolean bo;
                if(jo.getInt("etudiant_travail")==1){
                     bo=true;
                }else{
                     bo=false;
                }
                AncientEtudiant etu=new AncientEtudiant(jo.getInt("etudiant_id"),jo.getString("etudiant_nom"), jo.getString("etudiant_prenom"), jo.getInt("etudiant_telephone"),
                        jo.getString("etudiant_mail"),jo.getInt("etudiant_promo"),bo, jo.getInt("id_categorie"));
                etudiants.add(etu);
                System.out.println(jo);
            }
            accueilAdapter adapter=new accueilAdapter(etudiants);
            rvAccueil.setAdapter(adapter);
            rvAccueil.setLayoutManager(new LinearLayoutManager(this));

        } catch (JSONException e) {
            gereErreur(e);
        }

    }

    public void gereErreur(Throwable t){
        Toast.makeText(this,"pblm de co",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "prblm de co "+t
        );
    }
}