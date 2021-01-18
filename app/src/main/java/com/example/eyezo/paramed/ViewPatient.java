package com.example.eyezo.paramed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ViewPatient extends AppCompatActivity implements AdapterView.OnItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter incAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Patient> dataOfIncident;
    ListView logedInc;
    PatientListAdapter adapterInc;
    AlertDialog progressDailog;
    Dialog dialog;
    String objectId;

    TextView name, pid, gender, ethn, add, contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient);

        dataOfIncident = new ArrayList<>();
        logedInc = findViewById(R.id.pList);
        //logedInc.setOnItemClickListener(true);
        getPatients();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Patients");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AdminHome.class));
        ViewPatient.this.finish();
    }

    private void getPatients()
    {
        progressDailog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom)
                .build();
        progressDailog.show();
        Backendless.Persistence.of(Patient.class).find(new AsyncCallback<List<Patient>>() {
            @Override
            public void handleResponse(List<Patient> response) {
                dataOfIncident.clear();
                dataOfIncident = response;
                adapterInc = new PatientListAdapter(ViewPatient.this ,dataOfIncident);

                logedInc.setAdapter(adapterInc);
                progressDailog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(ViewPatient.this, "error "+ fault, Toast.LENGTH_SHORT).show();


            }
        });

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.full_patient_info);

        name = dialog.findViewById(R.id.patientName);
        pid = dialog.findViewById(R.id.patientId);
        gender = dialog.findViewById(R.id.patientGen);
        ethn = dialog.findViewById(R.id.patientEthn);
        add = dialog.findViewById(R.id.patientAdd);
        contact = dialog.findViewById(R.id.patientCont);

        objectId = dataOfIncident.get(position).getObjectId();

        name.setText(dataOfIncident.get(position).getName());
        pid.setText(dataOfIncident.get(position).getiD());
        gender.setText(dataOfIncident.get(position).getGender());
        ethn.setText(dataOfIncident.get(position).getEthnicity());
        add.setText(dataOfIncident.get(position).getAddress());
        contact.setText(dataOfIncident.get(position).getContact());
    }
}
