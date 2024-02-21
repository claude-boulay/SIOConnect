package com.example.sioconnect.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sioconnect.Accueil;
import com.example.sioconnect.AncientEtudiant.AncientEtudiant;
import com.example.sioconnect.Classe.Webservice;
import com.example.sioconnect.MainActivity;
import com.example.sioconnect.R;
import com.example.sioconnect.Travail.showYourJob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Profil extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String Token;
    RequestQueue fileRequetes;
    Spinner spinner;
    int random;
    int kalamar=555;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_profil);
        Intent profil=getIntent();
        Token=profil.getStringExtra("token");
        fileRequetes = Volley.newRequestQueue(this);
        //attribution du spinner de la vue
        spinner=findViewById(R.id.spinner);
        random=new Random().nextInt(1000);
        //intégration des donnée dans le spinner
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
                this,
                R.array.travail_categorie,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        requestProfil();

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
            Toast.makeText(this,"Vous êtes déjà sur l'onglet Profil",Toast.LENGTH_SHORT).show();
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

    public void requestProfil(){
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("getProfil");

        StringRequest getProfil= new StringRequest(Request.Method.GET,url,this::processProfil,this::gereErreur){
            public Map<String,String> getHeaders() throws AuthFailureError{
                Map<String,String> header=new HashMap<String,String>();
                header.put("Authorization",Token);
                return  header;
            }
        };

        fileRequetes.add(getProfil);
    }

    public void processProfil(String response){
        try {
            Log.e("reponse",response);
            JSONArray ja=new JSONArray(response);
            JSONObject ju=ja.getJSONObject(0);
            JSONObject je=ja.getJSONObject(1);
            User  utilisateur=new User(ju.getInt("Id_user"), ju.getString("identifiant"), ju.getString("nom_user"),ju.getString("prenom_user"),ju.getString("user_type"));

            //attribution des données user au champ
            TextView email=findViewById(R.id.identifiant);
            email.setText(utilisateur.getIdentifiant());
            TextView nom=findViewById(R.id.nom_user);
            nom.setText(utilisateur.getNom());
            TextView prenom=findViewById(R.id.prenom_user);
            prenom.setText(utilisateur.getPrenom());



            if(je!=null){
                boolean travail;
                if(je.getInt("etudiant_travail")==1 ){
                   travail=true;
                }else{
                    travail=false;
                }


                AncientEtudiant etu= new AncientEtudiant(je.getInt("etudiant_id"), je.getString("etudiant_nom"),je.getString("etudiant_prenom"),je.getString("etudiant_telephone"),je.getString("etudiant_mail"), je.getString("etudiant_promo"),travail, je.getInt("id_categorie") );
                TextView tel=findViewById(R.id.phonenumber);
                if(etu.getTelephone()!=null && etu.getTelephone()!=""){
                    tel.setText(etu.getTelephone());
                }
                if(etu.getPromo()!=null && etu.getPromo()!=""){
                    TextView promo=findViewById(R.id.user_promo);
                    promo.setText(etu.getPromo());
                }
                if(etu.getId_categorie()==12){

                    spinner.setSelection(10);
                }else{
                    spinner.setSelection(etu.getId_categorie()-1);
                }
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

    public void Enregistrer(View view){
        int index=spinner.getSelectedItemPosition();
        long id_cat=spinner.getAdapter().getItemId(index);

        System.out.println(id_cat);
        TextView email= findViewById(R.id.identifiant);
        TextView tel=findViewById(R.id.phonenumber);
        TextView nom=findViewById(R.id.nom_user);
        TextView prenom=findViewById(R.id.prenom_user);
        TextView promo=findViewById(R.id.user_promo);

        requestSendProfil(email.getText().toString(),tel.getText().toString(),nom.getText().toString(),prenom.getText().toString(),promo.getText().toString(),id_cat);
    }

    public void requestSendProfil(String email,String tel,String nom,String prenom,String promo,long id_cat){
        Webservice annuaire=new Webservice();
        String url=annuaire.Addurl("addInformation");
        String id= ""+id_cat;
        StringRequest sendProfil=new StringRequest(Request.Method.POST,url,this::processSendProfil,this::gereErreur){
            public Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String,String>();
                params.put("email",email);
                params.put("tel",tel);
                params.put("nom",nom);
                params.put("prenom",prenom);
                params.put("promo",promo);
                params.put("id_cat",id);

                return params;
            }
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", Token);
                return header;
            }

        };

        fileRequetes.add(sendProfil);

    }

    public void processSendProfil(String response){
        try {
            System.out.println(response);
            JSONObject ja=new JSONObject(response);
            System.out.println(ja);
            String retour=ja.getString("retour");
            switch (retour){
                case "mineur":
                    Toast.makeText(this,"changement effectué",Toast.LENGTH_SHORT).show();

                    break;
                case "important":
                    Intent test=new Intent(this, MainActivity.class);
                    Toast.makeText(this, "changement d'identifiant merci de vous reconnectez pour vos actions future",Toast.LENGTH_LONG).show();
                    startActivity(test);
                    break;
                case "incorrecte":
                    Toast.makeText(this,"Une donnée est incorecte: email non valide/promo supérieur ou inférieur a 4 chiffre/téléphone non valide",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            };
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Toast.makeText(this,"modification effectué",Toast.LENGTH_SHORT);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void SuppresCompte(View view){
        Webservice Annuaire=new Webservice();
        String url=Annuaire.Addurl("SuppressProfil");
        StringRequest stringRequest=new StringRequest(Request.Method.POST,url,this::suppressProfil,this::gereErreur){
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Authorization", Token);
                return header;
            }
        };
        fileRequetes.add(stringRequest);

    }

    public void suppressProfil(String response){
        try {
            JSONObject ja=new JSONObject(response);
            if (ja.getBoolean("retour")==true){
                Toast.makeText(this,"Demande de suppression enregistrer dans la base de donnée",Toast.LENGTH_LONG).show();

            }else if(ja.getBoolean("retour")==false){
                Toast.makeText(this,"Annulation de la suppression réussie",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"échec de la demande de suppression",Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            gereErreur(e);
        }

    }
}
