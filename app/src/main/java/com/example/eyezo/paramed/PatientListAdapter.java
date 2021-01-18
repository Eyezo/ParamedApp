package com.example.eyezo.paramed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientListAdapter extends ArrayAdapter<Patient> {
    private Context context;
    private List<Patient> pList;


    public PatientListAdapter(Context context, List<Patient> pList) {
        super(context, R.layout.patient_row, pList);
        this.context = context;
        this.pList = pList;
    }

    @NonNull
    @Override
    public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflator.inflate(R.layout.patient_row, parent, false);

        TextView patNam = row.findViewById(R.id.patName);
        TextView pId = row.findViewById(R.id.patId);
        TextView caseNumber = row.findViewById(R.id.patCaseNum);

        patNam.setText(pList.get(position).getName().toUpperCase());
        pId.setText(pList.get(position).getiD().toString());
        caseNumber.setText(pList.get(position).getIncidentNum().toString());

        return row;
    }
}
