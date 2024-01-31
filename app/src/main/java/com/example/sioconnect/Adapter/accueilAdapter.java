package com.example.sioconnect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.AncientEtudiant.AncientEtudiant;
import com.example.sioconnect.R;
import com.example.sioconnect.ViewHolder.accueilViewHolder;

import java.util.ArrayList;

public class accueilAdapter extends RecyclerView.Adapter<accueilViewHolder> {

    private ArrayList<AncientEtudiant> MesAncientsEtudiants;

    public accueilAdapter(ArrayList<AncientEtudiant> ae){MesAncientsEtudiants=ae;}

    @NonNull
    @Override
    public accueilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();

        LayoutInflater inflater=LayoutInflater.from(context);
        View AccueilView;
        AccueilView=inflater.inflate(R.layout.recyclerview_accueil,parent,false);
        accueilViewHolder viewHolder=new accueilViewHolder(AccueilView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull accueilViewHolder holder, int position) {
        AncientEtudiant ancientEtudiant=MesAncientsEtudiants.get(position);
        holder.bindData(ancientEtudiant);
    }

    @Override
    public int getItemCount() {
        return MesAncientsEtudiants.size();
    }
}
