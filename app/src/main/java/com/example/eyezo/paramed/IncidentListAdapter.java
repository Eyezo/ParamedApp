package com.example.eyezo.paramed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.backendless.BackendlessUser;

import java.util.List;

public class IncidentListAdapter extends ArrayAdapter<Incident> {
    private Context context;
    private List<Incident> incList;

    public IncidentListAdapter(Context context, List<Incident> incList){

        super(context, R.layout.incident_row, incList);
        this.context = context;
        this.incList = incList;
    }

    @NonNull
    @Override
    public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflator.inflate(R.layout.incident_row,parent,false);

        TextView medicName = row.findViewById(R.id.logger);
        TextView caseNumber = row.findViewById(R.id.incNum);
        TextView descript = row.findViewById(R.id.incD);

        medicName.setText(incList.get(position).getLoggedBy().toUpperCase());
        caseNumber.setText(incList.get(position).getCaseNumber());
        descript.setText(incList.get(position).getDescription());



        return row;
    }
}
