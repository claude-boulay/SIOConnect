package com.example.sioconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.Travail.showYourJob;
import com.example.sioconnect.user.Profil;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GraphicPercentWork extends AppCompatActivity {
    String Token;
    int random;
    int kalamar=555;
    RequestQueue Filerequete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_graphic_percent_work);
        Intent graph=getIntent();
        Token=graph.getStringExtra("token");
        Filerequete= Volley.newRequestQueue(this);
        random=new Random().nextInt(1000);
        requestGraph();
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
        if (item.getItemId() == R.id.menu_accueil) {
            Intent accueil=new Intent(this, Accueil.class);
            accueil.putExtra("token",Token);
            startActivity(accueil);
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
            Toast.makeText(this,"vous êtes déjà sur cette page",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void requestGraph(){
        Webservice annuaire=new Webservice();
        String url= annuaire.Addurl("percentWorkGraph");
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,this::processGraph,this::gereErreur) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", Token);
                return header;
            }
        };
        Filerequete.add(stringRequest);
    }

    public void gereErreur(Throwable t){
        Toast.makeText(this,"connexion dépassé reconnecté vous",Toast.LENGTH_LONG).show();
        Log.e("Connexion",
                "Rechargez la page "+t
        );
    }

    public void processGraph(String response){
        try {
            System.out.println(response);
            JSONObject jo=new JSONObject(response);
            ImageView graph=findViewById(R.id.graph);
            Picasso.get().load(jo.getString("url")).into(graph);


        } catch (JSONException e) {
            gereErreur(e);
        }
    }
}