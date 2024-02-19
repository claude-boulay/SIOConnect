package com.example.sioconnect.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.Organisation.Organisation;
import com.example.sioconnect.R;
import com.example.sioconnect.Travail.Travail;

public class YoursJobsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView org;
    TextView profession;
    TextView end;

    public YoursJobsViewHolder(@NonNull View itemView) {
        super(itemView);
        org=itemView.findViewById(R.id.organisation);
        profession=itemView.findViewById(R.id.profession);
        end=itemView.findViewById(R.id.annee_fin);
        itemView.setOnClickListener(this);
    }

    public void BindData(final Travail t){
        Organisation temp=t.getOrg();
        org.setText(temp.getNom());
        profession.setText(t.getProfession());
        end.setText(t.getFin().toString());
        org.setId(temp.getId());

    }

    @Override
    public void onClick(View view) {

    }
}
