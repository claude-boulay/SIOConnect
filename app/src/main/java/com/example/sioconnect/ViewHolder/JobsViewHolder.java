package com.example.sioconnect.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sioconnect.Organisation.Organisation;
import com.example.sioconnect.R;
import com.example.sioconnect.Travail.Travail;

public class JobsViewHolder extends RecyclerView.ViewHolder {
    TextView JobProfession;
    TextView JobDebut;
    TextView JobFin;
    TextView JobOrg;
    TextView JobOrgAdresse;
    TextView JobOrgSite;
    Travail emploi;
    Organisation org;

    public JobsViewHolder(@NonNull View itemView) {
        super(itemView);
        JobDebut=itemView.findViewById(R.id.JobDebut);
        JobFin=itemView.findViewById(R.id.JobFin);
        JobProfession=itemView.findViewById(R.id.jobProfession);
        JobOrg=itemView.findViewById(R.id.JobOrg);
        JobOrgAdresse=itemView.findViewById(R.id.JobOrgAdresse);
        JobOrgSite=itemView.findViewById(R.id.JobOrgSite);
    }

    public void BindData(final Travail T){
        emploi=T;
        org=emploi.getOrg();
        JobProfession.setText("Emploi : "+emploi.getProfession());
        JobFin.setText("Date de fin de l'emploi : "+emploi.getFin());
        JobDebut.setText("Date de début de l'emploi : "+emploi.getDebut());
        JobOrg.setText("Nom de l'Organisation : "+org.getNom());
        JobOrgAdresse.setText("Adresse de L'Organisation : "+org.getAdresse());
        JobOrgSite.setText("Site de l'organisation : "+org.getSite());

    }

}
