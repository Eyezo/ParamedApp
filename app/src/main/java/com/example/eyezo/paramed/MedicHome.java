package com.example.eyezo.paramed;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dmax.dialog.SpotsDialog;

public class MedicHome extends AppCompatActivity {

    EditText caseNum, caseDescription, loca, logBy, date;
    Dialog dlg;
    String caseNumber, description, local, caseDate, logger;




    Location location;
    double describeContents;
    Geocoder geocoder;
    String result;
    List<Address> addresses;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medic_home);

        caseNum = findViewById(R.id.etCaseNum);
        caseDescription = findViewById(R.id.etCaseDescrip);
        loca = findViewById(R.id.etCaseLocat);
        date = findViewById(R.id.etCaseDate);
        logBy = findViewById(R.id.etCaseLogBy);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Add Incident");

        String nme = getIntent().getStringExtra("name");
        logBy.setText(nme);



}
    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


        public void btnSaveIncident (View v){

            checkValues();

        }
        public void btnIncidentCancel (View v){

        }

        private void checkValues () {

            caseNumber = caseNum.getText().toString();
            description = caseDescription.getText().toString();
            local = loca.getText().toString();
            caseDate = date.getText().toString();
            logger = logBy.getText().toString();

            if (caseNumber.isEmpty() || description.isEmpty() || local.isEmpty() || caseDate.isEmpty() || logger.isEmpty()) {
                Toast.makeText(this, "All Fields are needed to Log Incident", Toast.LENGTH_SHORT).show();
            } else {
                if (connectionAvailable()) {
                    dlg = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                    dlg.show();
                    SaveIncident();
                    dlg.dismiss();
                    MedicHome.this.finish();

                    Intent intent = new Intent(MedicHome.this, PatientInformation.class);
                    intent.putExtra("caseNum", caseNumber);
                    startActivity(intent);

                    Toast.makeText(MedicHome.this, "Incident saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MedicHome.this, "No Internet connection", Toast.LENGTH_SHORT).show();
                }
            }

        }
        private void SaveIncident () {

            Incident incident = new Incident();

            incident.setCaseNumber(caseNumber);
            incident.setDescription(description);
            incident.setLocation(local);
            incident.setLoggedBy(logger);


            dlg = new SpotsDialog.Builder().setContext(this)
                    .setTheme(R.style.Custom).build();
            dlg.show();

            Backendless.Persistence.save(incident, new AsyncCallback<Incident>() {
                @Override
                public void handleResponse(Incident response) {

                    Toast.makeText(MedicHome.this, "Successfully created", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(MedicHome.this, "error " + fault.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_medic, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            int id = item.getItemId();
            if (id == R.id.menu_medic_logout) {
                dlg = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
                dlg.show();

                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        startActivity(new Intent(MedicHome.this, MainActivity.class));
                        MedicHome.this.finish();
                        dlg.dismiss();
                        Toast.makeText(MedicHome.this, "User Successfully logged out", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(MedicHome.this, "error " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        private boolean connectionAvailable ()
        {
            boolean connected = false;

            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    connected = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connected = true;
                }
            } else {
                connected = false;
            }
            return connected;
        }

    }



