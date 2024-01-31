package com.example.sioconnect.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.AncientEtudiant.AncientEtudiant;
import com.example.sioconnect.R;

public class accueilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View aview;
    TextView nomT;
    TextView prenomT;
    TextView promoT;
    AncientEtudiant etu;
    public accueilViewHolder(@NonNull View itemView) {
        super(itemView);
        nomT=itemView.findViewById(R.id.nom_etu);
        prenomT=itemView.findViewById(R.id.prenom_etu);
        promoT=itemView.findViewById(R.id.promo);
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

    }
}
