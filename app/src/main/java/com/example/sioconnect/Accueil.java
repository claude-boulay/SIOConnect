package com.example.sioconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.Travail.showYourJob;
import com.example.sioconnect.user.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Accueil extends AppCompatActivity {
    RequestQueue Filerequete;
    String Token;
    RecyclerView rvAccueil;

    ArrayList<AncientEtudiant> etudiants= new ArrayList<AncientEtudiant>();
    int random;
    int kalamar=555;

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
        random=new Random().nextInt(1000);

        RequestAccueil();


    }
//integration du menu dans la vue
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.menu_accueil);
        if (random==kalamar){

            item.setIcon(R.drawable.calamar);
        }

        return super.onPrepareOptionsMenu(menu);
    }
//function pour aplliquer des actions selon le choix de l'utilisateur dans le menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_accueil) {
            Toast.makeText(this,"vous êtes déjà sur l'accueil",Toast.LENGTH_SHORT).show();
            return true;
        }
         else if (item.getItemId()==R.id.profil) {
            Intent profil=new Intent(this, Profil.class);
            profil.putExtra("token",Token);
            startActivity(profil);
        }else if(item.getItemId()==R.id.addWork){
            Intent addWork=new Intent(this,com.example.sioconnect.Travail.addWork.class);
            addWork.putExtra("token",Token);
            startActivity(addWork);
        } else if (item.getItemId()==R.id.showEmploi) {
            Intent showEmploi=new Intent(this, showYourJob.class);
            showEmploi.putExtra("token",Token);
            startActivity(showEmploi);
        } else if (item.getItemId()==R.id.graphEmploi) {
             Intent graphEmploi=new Intent(this, GraphicPercentWork.class);
             graphEmploi.putExtra("token",Token);
             startActivity(graphEmploi);
        }
        return super.onOptionsItemSelected(item);
    }

    public void RequestAccueil(){
        Webservice annuaire=new Webservice();
        String url= annuaire.Addurl("getAncienEtudiants");
        StringRequest stringRequest=new StringRequest(Request.Method.GET,url,this::processAccueil,this::gereErreur){
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
            System.out.println(response);
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
                AncientEtudiant etu=new AncientEtudiant(jo.getInt("etudiant_id"),jo.getString("etudiant_nom"), jo.getString("etudiant_prenom"), jo.getString("etudiant_telephone"),
                        jo.getString("etudiant_mail"),jo.getString("etudiant_promo"),bo, 0);
                etudiants.add(etu);

            }
            accueilAdapter adapter=new accueilAdapter(etudiants,Token);
            rvAccueil.setAdapter(adapter);
            rvAccueil.setLayoutManager(new LinearLayoutManager(this));

        } catch (JSONException e) {
            gereErreur(e);
        }

    }

    public void gereErreur(Throwable t){
        Toast.makeText(this,"connexion dépassé reconnecté vous",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "Rechargez la page "+t
        );
    }
}