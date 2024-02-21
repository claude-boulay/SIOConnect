package com.example.sioconnect.Travail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Accueil;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.Organisation.Organisation;
import com.example.sioconnect.R;
import com.example.sioconnect.user.Profil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EndYourJob extends AppCompatActivity {
    String Token;
    int idorg;

    RequestQueue fileRequete;

    TextView prof;
    TextView orgN;
    TextInputLayout fin;
    TextInputEditText  site;
    EditText tel;
    int random;
    int kalamar=555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_end_your_job);
        Intent i=getIntent();
        Token=i.getStringExtra("token");
        idorg=i.getIntExtra("idJob",0);
        fileRequete= Volley.newRequestQueue(this);
        random=new Random().nextInt(1000);
        requestJob();
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
        } else if (item.getItemId()==R.id.addWork) {
            Intent addWork=new Intent(this, com.example.sioconnect.Travail.addWork.class);
            addWork.putExtra("token",Token);
            startActivity(addWork);
        }else if (item.getItemId()==R.id.showEmploi) {
            Intent showEmploi=new Intent(this, showYourJob.class);
            showEmploi.putExtra("token",Token);
            startActivity(showEmploi);
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestJob(){
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("getAJob");

        StringRequest StringRequest=new StringRequest(Request.Method.POST,url, this::processJob,this::gereErreur){

            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }

            public Map<String,String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("idOrg",""+idorg);
                return params;
            }

        };
        fileRequete.add(StringRequest);
    }

    public void processJob(String response){
        try {
            JSONArray ja =new JSONArray(response);
            JSONObject jo=ja.getJSONObject(0);
            Organisation org=new Organisation(jo.getInt("organisation_id"),jo.getString("organisation_nom"),jo.getString("organisation_adresse"),jo.getString("organisation_tel"), jo.getString("organisation_site") );
            JSONObject joe=jo.getJSONObject("pivot");
            Travail emploi=new Travail(org,joe.getString("profession"),joe.getString("annee_debut"), joe.getString("annee_fin") );

             prof=findViewById(R.id.Myprofession);
            prof.setText(emploi.getProfession());
             orgN=findViewById(R.id.Myorg);
            orgN.setText(org.getNom());
            TextView debut=findViewById(R.id.Mydate_debut);
            debut.setText(emploi.getDebut());
             fin=findViewById(R.id.MydateFin);
            fin.getEditText().setText(emploi.getFin());
             tel=findViewById(R.id.MyorgPhone);
            tel.setText(org.getTel());
             site=findViewById(R.id.Myorgsite);
            site.setText(org.getSite());


        }catch (Exception e){
            gereErreur(e);
        }
    }

    public void gereErreur(Throwable t){
        Toast.makeText(this,"connexion dépassé reconnecté vous",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "prblm de co "+t
        );
    }

    public void ModifJob(View view){
        String telorg=tel.getText().toString();
        String siteorg=site.getText().toString();
        String finTravail=fin.getEditText().getText().toString();
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("UpdateJob");
        requestMJob(telorg,siteorg,finTravail,url);
    }

    public void requestMJob(String telorg,String siteorg,String finTravail,String url){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,this::processMJob,this::gereErreur){
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }

            public Map<String,String> getParams() {
                Map<String,String> params=new HashMap<>();
                params.put("idorg",""+idorg);
                params.put("telorg",telorg);
                params.put("siteorg",siteorg);
                params.put("finTravail",finTravail);

                return params;
            }
        };
        fileRequete.add(stringRequest);
    }

    public void processMJob(String response){
        JSONObject jo= null;
        try {
            jo = new JSONObject(response);

        if(jo.getBoolean("retour")==true){
            Toast.makeText(this,"modification enregistrée", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(this,"donnée non correspondante au format attendu",Toast.LENGTH_SHORT).show();
        } } catch (JSONException e) {
        throw new RuntimeException(e);
    }

    }

}