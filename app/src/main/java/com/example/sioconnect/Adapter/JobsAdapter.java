package com.example.sioconnect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.R;
import com.example.sioconnect.Travail.Travail;
import com.example.sioconnect.ViewHolder.JobsViewHolder;
import com.example.sioconnect.ViewHolder.accueilViewHolder;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsViewHolder> {
    ArrayList<Travail> Sesemploi;

    public JobsAdapter(ArrayList<Travail> emplois){Sesemploi=emplois;}
    @NonNull
    @Override
    public JobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View JobsView;
        JobsView=inflater.inflate(R.layout.recyclerview_jobs,parent,false);
        JobsViewHolder viewHolder=new JobsViewHolder(JobsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobsViewHolder holder, int position) {
        Travail emploi=Sesemploi.get(position);
        holder.BindData(emploi);


    }

    @Override
    public int getItemCount() {
        return Sesemploi.size();
    }
}
