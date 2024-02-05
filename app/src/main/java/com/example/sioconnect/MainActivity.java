package com.example.sioconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Classe.Webservice;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String id;
    String pass;
    RequestQueue fileRequete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileRequete= Volley.newRequestQueue(this);
    }

    public void Connexion(View MainView){

        TextView identifiant=findViewById(R.id.Identifiant);
        TextView password=findViewById(R.id.password);
        id=identifiant.getText().toString();
        pass=password.getText().toString();
        System.out.println("id="+id+" pass="+pass);

        requestConnexion();
    }

    public void requestConnexion(){
        Webservice Annuaire=new Webservice();
        String url =Annuaire.Addurl("connexion");
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                url,
                this::processConnexion,
                this::gereErreur
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Identifiant",id);
                params.put("mdp",pass);
                return params;
            }
        };
        fileRequete.add(stringRequest);
    }

    public void processConnexion(String response){
        try {
            JSONObject jo=new JSONObject(response);
            if(jo.getString("message")!="Login failed"){
                System.out.println(jo);

                String Token=jo.getString("token");

                Log.e("Token",Token);
                Intent i= new Intent(this, Accueil.class);
                i.putExtra("token",Token);


                Toast.makeText(this,jo.getString("message"), Toast.LENGTH_LONG).show();
                startActivity(i);
            }else{
                Toast.makeText(this,"Identifiant ou mot de passe erroné",Toast.LENGTH_LONG).show();
            }


        }catch(Exception e){
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