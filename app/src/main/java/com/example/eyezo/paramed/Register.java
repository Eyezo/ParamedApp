package com.example.eyezo.paramed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import java.util.List;
import dmax.dialog.SpotsDialog;

public class Register extends AppCompatActivity {

    EditText etNmes, etEmpNum, etEmail, etPass, etRetypePass;
    AlertDialog dlg;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        etNmes = (EditText)findViewById(R.id.etName);
        etEmpNum = (EditText)findViewById(R.id.etEmpNumber);
        etEmail = (EditText)findViewById(R.id.etEmpEmail);
        etPass = (EditText)findViewById(R.id.etPassword);
        etRetypePass = (EditText)findViewById(R.id.etRetypePassword);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Register");
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void btnReg(View v){

        checkValues();

    }
    public void btnCancel(View v){

        Register.this.finish();
    }

    private void checkValues(){

        String fullName = etNmes.getText().toString();
        String employeeNum = etEmpNum.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPass.getText().toString();
        String passwordRetype = etRetypePass.getText().toString();

        if (fullName.isEmpty() || employeeNum.isEmpty() || email.isEmpty() || password.isEmpty() || passwordRetype.isEmpty())
        {
            Toast.makeText(this,"All Fields are needed to register", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(passwordRetype)){
            Toast.makeText(this,"Passwords must match", Toast.LENGTH_SHORT).show();
        }
        else{
            if(connectionAvailable()){
                dlg = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
                dlg.show();
                registerUser();
                dlg.dismiss();
                Register.this.finish();

            }
            else{
                Toast.makeText(Register.this, "No Internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void registerUser(){
        BackendlessUser user = new BackendlessUser();
        user.setProperty("email", etEmail.getText().toString().trim());
        user.setProperty("employeeId", etEmpNum.getText().toString().trim());
        user.setProperty("name", etNmes.getText().toString().trim());
        user.setPassword(etPass.getText().toString().trim());
        user.setProperty("role", "none");

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {

                Toast.makeText(Register.this, response.getProperty("name")+ " registered successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                dlg.dismiss();
                Toast.makeText(Register.this, "error"+ fault.getDetail(), Toast.LENGTH_SHORT).show();

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
