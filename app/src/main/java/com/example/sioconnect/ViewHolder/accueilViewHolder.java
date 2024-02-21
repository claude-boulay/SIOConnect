package com.example.sioconnect.ViewHolder;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.AncientEtudiant.AncientEtudiant;
import com.example.sioconnect.AncientEtudiant.jobsEtudiant;
import com.example.sioconnect.R;

public class accueilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View aview;
    TextView nomT;
    TextView prenomT;
    TextView promoT;
    AncientEtudiant etu;
    String Token;
    public accueilViewHolder(@NonNull View itemView, String token) {
        super(itemView);
        nomT=itemView.findViewById(R.id.nom_etu);
        prenomT=itemView.findViewById(R.id.prenom_etu);
        promoT=itemView.findViewById(R.id.promo);

        Token=token;
        itemView.setOnClickListener( this);
    }

    public void bindData(final AncientEtudiant ae){
        etu=ae;
        nomT.setText(etu.getNom());
        nomT.setId(etu.getId());
        prenomT.setText(etu.getPrenom());
        String promo=""+etu.getPromo();
        promoT.setText(promo);
    }

    @Override
    public void onClick(View view) {
        Intent jobsEtudiant=new Intent(itemView.getContext(),com.example.sioconnect.AncientEtudiant.jobsEtudiant.class);
        jobsEtudiant.putExtra("token",Token);
        jobsEtudiant.putExtra("idEtu",nomT.getId());
        startActivity(itemView.getContext(),jobsEtudiant,null);
    }
}
