package com.example.sioconnect.Travail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.R;
import com.example.sioconnect.user.Profil;

public class showYourJob extends AppCompatActivity {
    String Token;
    RequestQueue fileRequete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_show_your_job);
        Intent accueil=getIntent();
        Token=accueil.getStringExtra("token");
        fileRequete= Volley.newRequestQueue(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    //function pour aplliquer des actions selon le choix de l'utilisateur dans le menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.menu_base) {

            return true;
        } else if (item.getItemId() == R.id.menu_settings) {

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
            Intent showEmploi=new Intent(this, showYourJob.class);
            showEmploi.putExtra("token",Token);
            startActivity(showEmploi);
        }
        return super.onOptionsItemSelected(item);
    }


}