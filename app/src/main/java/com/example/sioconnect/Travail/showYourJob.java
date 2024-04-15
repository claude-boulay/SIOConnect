package com.example.sioconnect.Travail;

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
import com.example.sioconnect.Accueil;
import com.example.sioconnect.Adapter.YoursJobsAdapter;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.GraphicPercentWork;
import com.example.sioconnect.Organisation.Organisation;
import com.example.sioconnect.R;
import com.example.sioconnect.user.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class showYourJob extends AppCompatActivity {
    String Token;
    RequestQueue fileRequete;

    RecyclerView rvs;
    ArrayList<Travail> mesTravails=new ArrayList<Travail>();
    int random;
    int kalamar=555;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_show_your_job);
        Intent accueil=getIntent();
        Token=accueil.getStringExtra("token");
        fileRequete= Volley.newRequestQueue(this);
        rvs=(RecyclerView) findViewById(R.id.recyclerViewYourJob);
        random=new Random().nextInt(1000);
        requestYoursJob();
    }
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
        if (item.getItemId() == R.id.menu_base) {

            return true;
        } else if (item.getItemId() == R.id.menu_accueil) {
            Intent accueil=new Intent(this, Accueil.class);
            accueil.putExtra("token",Token);
            startActivity(accueil);
            return true;
        } else if (item.getItemId()==R.id.profil) {
            Intent profil=new Intent(this, Profil.class);
            profil.putExtra("token",Token);
            startActivity(profil);
        }else if(item.getItemId()==R.id.addWork){
            Intent addWork=new Intent(this,com.example.sioconnect.Travail.addWork.class);
            addWork.putExtra("token",Token);
            startActivity(addWork);
        } else if (item.getItemId()==R.id.showEmploi) {
            Toast.makeText(this,"vous êtes déjà sur l'onglet vous affichant vos emplois",Toast.LENGTH_SHORT).show();
        }else if (item.getItemId()==R.id.graphEmploi) {
            Intent graphEmploi=new Intent(this, GraphicPercentWork.class);
            graphEmploi.putExtra("token",Token);
            startActivity(graphEmploi);
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestYoursJob(){
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("showYoursJobs");

        StringRequest getProfil= new StringRequest(Request.Method.GET,url,this::processYoursJobs,this::gereErreur){
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }
        };

        fileRequete.add(getProfil);
    }

    public void processYoursJobs(String response){
        try {
            System.out.println(response);
            JSONObject jo=new JSONObject(response);
            System.out.println(jo.getString("retour"));
            if (jo.getBoolean("retour")==true){

                JSONArray ja=jo.getJSONArray("travails");

                int syze=ja.length();
                for (int i=0;i<syze;i++){
                    JSONObject JoT=ja.getJSONObject(i);
                    System.out.println(JoT);
                    Organisation org=new Organisation(JoT.getInt("organisation_id"),JoT.getString("organisation_nom"),JoT.getString("organisation_adresse"),JoT.getString("organisation_tel"),JoT.getString("organisation_site"));
                    JSONObject JOTA=JoT.getJSONObject("pivot");
                    Travail unEmploi=new Travail(org,JOTA.getString("profession"),JOTA.getString("annee_debut"),JOTA.getString("annee_fin"));
                    System.out.println(unEmploi);
                    mesTravails.add(unEmploi);
                }
                YoursJobsAdapter adapter=new YoursJobsAdapter(mesTravails,Token);
                rvs.setAdapter(adapter);
                rvs.setLayoutManager(new LinearLayoutManager(this));
            }else if(jo.getString("retour")=="Null"){
                Toast.makeText(this,"Vous n'avez pas encore enregistrer d'emploi",Toast.LENGTH_LONG);
            }else{
                Toast.makeText(this,"Erreur inconnu",Toast.LENGTH_LONG);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    public void gereErreur(Throwable t){
        Toast.makeText(this,"connexion dépassé reconnecté vous",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "prblm de co "+t
        );
    }

}