package com.example.eyezo.paramed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder>
{

    private List<Patient> patList;
    IncidentAdapter.ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public PatientAdapter (Context context, List<Patient> list)
    {
        this.patList = list;
        activity = (IncidentAdapter.ItemClicked) context;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView patName, pId, caseNum;

        public ViewHolder(View itemView) {
            super(itemView);

            patName = itemView.findViewById(R.id.patName);
            pId = itemView.findViewById(R.id.patId);
            caseNum = itemView.findViewById(R.id.patCaseNum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(patList.indexOf((Patient) v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public PatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(patList.get(i));


        viewHolder.patName.setText(patList.get(i).getName().toString());
        viewHolder.pId.setText(patList.get(i).getiD().toString());
        viewHolder.caseNum.setText(patList.get(i).getIncidentNum());

    }

    @Override
    public int getItemCount() {
        return patList.size();
    }
}
