package com.example.sioconnect.Travail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class addWork extends AppCompatActivity {

    String Token;
    RequestQueue fileRequete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_add_work);
        Intent profil=getIntent();
        Token=profil.getStringExtra("token");
        fileRequete= Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_base) {

            return true;
        } else if (item.getItemId() == R.id.menu_settings) {

            return true;
        } else if (item.getItemId()==R.id.profil) {
            Intent profil=new Intent(this, com.example.sioconnect.user.Profil.class);
            profil.putExtra("token",Token);
            startActivity(profil);
        } else if (item.getItemId()==R.id.addWork) {
            Toast.makeText(this,"Vous êtes déjà sur l'onglet d'Ajout de travail",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    public void sendJob(View view){
        Webservice annuaire=new Webservice();
        String url= annuaire.Addurl("createJob");
        //recup les données des textview
        TextView entreprise=findViewById(R.id.new_nom_org);
        String entrepris=entreprise.getText().toString();
        TextView emploitext=findViewById(R.id.new_emploi);
        String emploi=emploitext.getText().toString();
        TextView adresseText=findViewById(R.id.new_org_adresse);
        String adresse=adresseText.getText().toString();
        TextView telText=findViewById(R.id.new_org_tel);
        String tel=telText.getText().toString();
        TextView sitetext=findViewById(R.id.new_org_site);
        TextView startDateText=findViewById(R.id.new_date_debut);
        TextView enddatetext=findViewById(R.id.new_date_fin);
        String site=sitetext.getText().toString();
        String startdate=startDateText.getText().toString();
        String endDate=enddatetext.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,this::proccesCreateJob,this::gereErreur){
            public Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("emploi",emploi);
                params.put("entreprise",entrepris);
                params.put("adresse",adresse);
                params.put("tel",tel);
                params.put("site",site);
                params.put("startDate",startdate);
                params.put("endDate",endDate);
                return params;
            }

            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> headers=new HashMap<String,String>();
                headers.put("Authorization",Token);
                return headers;
            }

        };
        fileRequete.add(stringRequest);
    }

    public void proccesCreateJob(String response){
        try {
            System.out.println(response);
            JSONObject jo=new JSONObject(response);
            switch (jo.getString("retour")){
                case "success":
                    Toast.makeText(this,"l'emploi à bel et bien été crée",Toast.LENGTH_LONG);
                    finish();
                    break;
                case "failure":
                    Toast.makeText(this,"échec de la création de l'emploi",Toast.LENGTH_SHORT);
                    break;
                default:
                    Toast.makeText(this,"erreur serveur",Toast.LENGTH_SHORT);
                    break;
            }
        } catch (JSONException e) {
           gereErreur(e);
        }

    }


    public void gereErreur(Throwable t){
        Toast.makeText(this,"connexion dépassé reconnecté vous",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "prblm de co "+t
        );
    }
}