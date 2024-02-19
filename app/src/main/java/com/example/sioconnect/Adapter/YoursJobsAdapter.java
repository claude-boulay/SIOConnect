package com.example.sioconnect.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.R;
import com.example.sioconnect.Travail.Travail;
import com.example.sioconnect.ViewHolder.YoursJobsViewHolder;
import com.example.sioconnect.ViewHolder.accueilViewHolder;

import java.util.ArrayList;

public class YoursJobsAdapter extends RecyclerView.Adapter<YoursJobsViewHolder>{

    private ArrayList<Travail> Travails;
    public YoursJobsAdapter(ArrayList<Travail> T){
        this.Travails=T;
    }

    @NonNull
    @Override
    public YoursJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View YoursJobs;
        YoursJobs=inflater.inflate(R.layout.recyclerview_yours_jobs,parent,false);
        YoursJobsViewHolder viewHolder=new YoursJobsViewHolder(YoursJobs);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull YoursJobsViewHolder holder, int position) {
        Travail Travail=Travails.get(position);
        holder.BindData(Travail);
    }

    @Override
    public int getItemCount() {
        return Travails.size();
    }
}
