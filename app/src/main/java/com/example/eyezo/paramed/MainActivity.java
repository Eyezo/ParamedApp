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

public class MainActivity extends AppCompatActivity {

    Dialog dialog;

    EditText empNum, password, txtEmailAddressReset;


    Button btnCancel, btnReset,txtForgPass, txtReg;

    AlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        empNum = findViewById(R.id.etLogEmpNumber);
        password = findViewById(R.id.etLogEmpPassword);

        progressDialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        actionBar.setIcon(R.mipmap.ic_launcher);

        showProgress(true);
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if(response)
                {
                    String userObjId = UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(userObjId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            if(response.getProperty("role").equals("Admin"))
                            {
                                Intent intent = new Intent(MainActivity.this, AdminHome.class);
                                intent.putExtra("name", (String) response.getProperty("name"));
                                intent.putExtra("role", (String) response.getProperty("role"));

                                startActivity(intent);
                                showProgress(false);
                                MainActivity.this.finish();
                            }
                            else if(response.getProperty("role").equals("Medic"))
                            {
                                Intent intent = new Intent(MainActivity.this, MedicHome.class);
                                intent.putExtra("name", (String) response.getProperty("name"));
                                intent.putExtra("role", (String) response.getProperty("role"));

                                startActivity(intent);
                                showProgress(false);
                                MainActivity.this.finish();
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                            Toast.makeText(MainActivity.this, "error "+ fault.getDetail(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MainActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.this.finish();
    }

    public void btnSubmit(View v)
    {
        String email = empNum.getText().toString().trim();
        String pssword = password.getText().toString().trim();

        if(email.isEmpty() || pssword.isEmpty())
        {
            Toast.makeText(this, "email and password are required to authenticate", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(connectionAvailable())
            {
                validateUser();

                // startActivity(new Intent(MainActivity.this, AdminHomePage.class));

            }
            else
            {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            }


        }

    }
    public void btnResetPassword(View v)
    {
        dialogImage();
    }
    public void btnRegisterUser(View v)
    {
        startActivity(new Intent(this, Register.class));
    }
    private void dialogImage()
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.forgot_password);
        btnCancel = dialog.findViewById(R.id.btnForgotCancel);
        btnReset = dialog.findViewById(R.id.btnForgotReset);
        txtEmailAddressReset = dialog.findViewById(R.id.edForgotEmail);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = txtEmailAddressReset.getText().toString().trim();

                if(em.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please provide an email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (connectionAvailable())
                    {
                        Backendless.UserService.restorePassword(em, new AsyncCallback<Void>() {
                            @Override
                            public void handleResponse(Void response) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "email sent successfully", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
    private void showProgress(boolean show)
    {
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    private void loginUser(String role)
    {
        if(role.equals("none"))
        {
            showProgress(false);
            Toast.makeText(this, "Please contact your Admin to assign you a role before you can log in!!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Backendless.UserService.login(empNum.getText().toString().trim(), password.getText().toString().trim(), new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {
                    if(response.getProperty("role").equals("Admin"))
                    {
                        Intent intent = new Intent(MainActivity.this, AdminHome.class);
                        intent.putExtra("name", (String) response.getProperty("name"));
                        intent.putExtra("role", (String) response.getProperty("role"));
                        intent.putExtra("employeeNumber", (String) response.getProperty("employeeId"));
                        startActivity(intent);
                        showProgress(false);
                        MainActivity.this.finish();
                    }
                    else
                    {
                        Intent intent = new Intent(MainActivity.this, MedicHome.class);
                        intent.putExtra("name", (String) response.getProperty("name"));
                        intent.putExtra("role", (String) response.getProperty("role"));
                        intent.putExtra("employeeNumber", (String) response.getProperty("employeeId"));
                        startActivity(intent);
                        showProgress(false);
                        MainActivity.this.finish();
                    }
                    MainActivity.this.finish();
                    Toast.makeText(MainActivity.this, "User successfully logged in", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    showProgress(false);
                    Toast.makeText(MainActivity.this, "error " + fault.getDetail(), Toast.LENGTH_SHORT).show();
                }
            },true);
        }
    }
    private void validateUser()
    {
        //Checks if user trying to log in is registered and retrieves the role of the user.
        showProgress(true);

        Backendless.Persistence.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                for(int x = 0; x < response.size();x++)
                {
                    if(response.get(x).getProperty("employeeId").equals(empNum.getText().toString().trim()))
                    {
                        loginUser((String) response.get(x).getProperty("role"));


                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
                Toast.makeText(MainActivity.this, "error" + " " + fault.getDetail(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean connectionAvailable() {
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
