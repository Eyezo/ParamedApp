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
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ViewIncident extends AppCompatActivity implements AdapterView.OnItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter incAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Incident> dataOfIncident;
    ListView logedInc;
    IncidentListAdapter adapterInc;
    AlertDialog progressDailog;
    Dialog dialog;
    String incId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);






        dataOfIncident = new ArrayList<>();
        logedInc = findViewById(R.id.viewList);
       //logedInc.setOnItemClickListener(true);
        getIncidents();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Incidents");


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AdminHome.class));
        ViewIncident.this.finish();
    }

    private void getIncidents()
    {
        progressDailog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom)
                .build();
        progressDailog.show();
        Backendless.Persistence.of(Incident.class).find(new AsyncCallback<List<Incident>>() {
            @Override
            public void handleResponse(List<Incident> response) {
                dataOfIncident.clear();
                dataOfIncident = response;
                adapterInc = new IncidentListAdapter(ViewIncident.this ,dataOfIncident);

                logedInc.setAdapter(adapterInc);
                progressDailog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(ViewIncident.this, "error "+ fault, Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(ViewIncident.this, AdminHome.class);
       // intent.putExtra("name", (String) response.getProperty("name"));
       // intent.putExtra("role", (String) response.getProperty("role"));
      //  intent.putExtra("employeeNumber", (String) response.getProperty("employeeId"));
        startActivity(intent);

        ViewIncident.this.finish();

    }
}
