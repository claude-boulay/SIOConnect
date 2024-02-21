package com.example.sioconnect.AncientEtudiant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Accueil;
import com.example.sioconnect.Adapter.JobsAdapter;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.Organisation.Organisation;
import com.example.sioconnect.R;
import com.example.sioconnect.Travail.Travail;
import com.example.sioconnect.Travail.showYourJob;
import com.example.sioconnect.user.Profil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class jobsEtudiant extends AppCompatActivity {

    String Token;
    RecyclerView rv;
    RequestQueue fileRequete;
    int idEtu;
    ArrayList<Travail> SesEmplois=new ArrayList<Travail>();
    int random;
    int kalamar=555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_jobs_etudiant);
        Intent here=getIntent();
        Token=here.getStringExtra("token");
        idEtu=here.getIntExtra("idEtu",0);
        fileRequete= Volley.newRequestQueue(this);
        rv=(RecyclerView) findViewById(R.id.recyclerViewJobs);
        random=new Random().nextInt(1000);
        requestJobs();

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
        if (item.getItemId() == R.id.menu_accueil) {
            Intent accueil=new Intent(this, Accueil.class);
            accueil.putExtra("token",Token);
            startActivity(accueil);
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
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestJobs(){
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("allEtudiantJobs");
        StringRequest strRequte=new StringRequest(Request.Method.POST,url,this::processJobs,this::gereErreur){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }

            public Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("id",""+idEtu);
                return params;
            }
        };
        fileRequete.add(strRequte);
    }

    public void processJobs(String response){
        try {
            System.out.println(response);
            JSONObject joO=new JSONObject(response);
            if (joO.getBoolean("retour")==true){

                JSONObject etudiant=joO.getJSONObject("etudiant");
                AncientEtudiant etu=new AncientEtudiant();
                etu.setNom(etudiant.getString("etudiant_nom"));
                etu.setPrenom(etudiant.getString("etudiant_prenom"));
                etu.setPromo(etudiant.getString("etudiant_promo"));

                TextView Nom=findViewById(R.id.aNometu);
                TextView Prenom=findViewById(R.id.aPrenomEtu);
                TextView Promo=findViewById(R.id.ApromoEtu);

                Nom.setText("Nom de l'étudiant : "+etu.getNom());
                Prenom.setText("Prénom de l'étudiant : "+etu.getPrenom());
                Promo.setText("Promo de l'étudiant : "+etu.getPromo());

                JSONArray emplois=joO.getJSONArray("emplois");
                int syze=emplois.length();
                for(int i=0;i<syze;i++){
                    JSONObject Emploi=emplois.getJSONObject(i);
                    Organisation entreprise=new Organisation(Emploi.getInt("organisation_id"),
                            Emploi.getString("organisation_nom"),Emploi.getString("organisation_adresse"),
                            Emploi.getString("organisation_tel"),Emploi.getString("organisation_site"));
                    JSONObject pivot=Emploi.getJSONObject("pivot");
                    Travail T=new Travail(entreprise, pivot.getString("profession"), pivot.getString("annee_debut"),pivot.getString("annee_fin") );

                    SesEmplois.add(T);
                }

                JobsAdapter adapter=new JobsAdapter(SesEmplois);
                rv.setAdapter(adapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }
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