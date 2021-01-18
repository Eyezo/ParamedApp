package com.example.eyezo.paramed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.backendless.Backendless;

import java.util.ArrayList;
import java.util.List;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.ViewHolder>
{

    private  List<Incident> inc;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public IncidentAdapter (Context context, List<Incident> list)
    {
        this.inc = list;
        activity = (ItemClicked) context;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,incDescript, caseNum;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.logger);
            caseNum = itemView.findViewById(R.id.incNum);
            incDescript = itemView.findViewById(R.id.incD);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(inc.indexOf((Incident) v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public IncidentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incident_row, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(inc.get(i));
        viewHolder.name.setText(inc.get(i).getLoggedBy().toString());
        viewHolder.caseNum.setText(inc.get(i).getCaseNumber().toString());
        viewHolder.incDescript.setText(inc.get(i).getDescription().toString());

    }

    @Override
    public int getItemCount() {
        return inc.size();
    }
}
