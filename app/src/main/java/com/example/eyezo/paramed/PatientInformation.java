package com.example.eyezo.paramed;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import dmax.dialog.SpotsDialog;

public class PatientInformation extends AppCompatActivity {

    Spinner spEthnicity, spGender;
    EditText edIdNum, edName, edSur, edAddr, edContact, edKinName, edKinSur;

    String eth, gen, id, name, surname, addr, cont, kinNme, kinSur, incidNum;

    String [] ethnicit = {"African", "White", "Coloured", "Indian"};
    String [] gender = {"Male", "Female"};

    Dialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);

        spEthnicity = findViewById(R.id.spEthnicity);
        spGender = findViewById(R.id.spGender);
        edIdNum = findViewById(R.id.pID);
        edName = findViewById(R.id.pName);
        edSur = findViewById(R.id.pSurname);
        edAddr = findViewById(R.id.pAddress);
        edContact = findViewById(R.id.pAddress);
        edKinName = findViewById(R.id.pNextKinName);
        edKinSur = findViewById(R.id.pSurname);

        incidNum = getIntent().getStringExtra("caseNum");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Patient Information");

        ArrayAdapter<String> choice = new ArrayAdapter<>(this, R.layout.spinner_item,ethnicit);
        spEthnicity.setAdapter(choice);
        ArrayAdapter<String> choice2 = new ArrayAdapter<>(this, R.layout.spinner_item,gender);
        spGender.setAdapter(choice2);

        spEthnicity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if(selectedItem.equals("African"))
                {
                    eth = "African";
                }
                else if (selectedItem.equals("White"))
                {
                    eth = "White";
                }
                else if (selectedItem.equals("Coloured"))
                {
                    eth = "coloured";
                }
                else if (selectedItem.equals("Indian"))
                {
                    eth = "Indian";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String selected = parent.getItemAtPosition(position).toString();

               if (selected.equals("Male"))
               {
                   gen = "Male";
               }
               else if (selected.equals("Female"))
               {
                   gen = "Female";
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void btnSave(View v){

        checkValues();

    }

    private void checkValues(){

        id = edIdNum.getText().toString();
        name = edName.getText().toString();
        surname = edSur.getText().toString();
        addr = edAddr.getText().toString();
        cont = edContact.getText().toString();
        kinNme = edKinName.getText().toString();
        kinSur = edKinSur.getText().toString();

        if (id.isEmpty() || name.isEmpty() || surname.isEmpty() || addr.isEmpty() || cont.isEmpty() || kinNme.isEmpty() || kinSur.isEmpty() || eth.isEmpty() || gen.isEmpty()){

            Toast.makeText(this,"All Fields are needed to Save Patient information", Toast.LENGTH_SHORT).show();
        }
        else{
            if(connectionAvailable()){
                dlg = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                dlg.show();
                SavePatient();
                dlg.dismiss();
                PatientInformation.this.finish();
                startActivity(new Intent(PatientInformation.this, SpeechReporting.class));
                Toast.makeText(PatientInformation.this, "Patient saved successfully", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(PatientInformation.this, "No Internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void SavePatient() {
        Patient patient = new Patient();

        patient.setiD(id);
        patient.setEthnicity(eth);
        patient.setName(name);
        patient.setSurname(surname);
        patient.setGender(gen);
        patient.setAddress(addr);
        patient.setContact(cont);
        patient.setNextKinName(kinNme);
        patient.setNextKinSur(kinSur);
        patient.setIncidentNum(incidNum);

        dlg = new SpotsDialog.Builder().setContext(this)
                .setTheme(R.style.Custom).build();
        dlg.show();

        Backendless.Persistence.save(patient, new AsyncCallback<Patient>() {
            @Override
            public void handleResponse(Patient response) {

                Toast.makeText(PatientInformation.this, "Successfully created", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(PatientInformation.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private boolean connectionAvailable()
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
