package com.example.sioconnect.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.sioconnect.R;

public class Profil extends AppCompatActivity {

    String Token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_profil);
        Intent profil=getIntent();
        Token=profil.getStringExtra("token");
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
            Intent profil=new Intent(this, Profil.class);
            profil.putExtra("token",Token);
            startActivity(profil);
        }
        return super.onOptionsItemSelected(item);
    }
}