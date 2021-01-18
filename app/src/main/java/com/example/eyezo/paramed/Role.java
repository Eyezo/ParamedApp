package com.example.eyezo.paramed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Role extends AppCompatActivity implements AdapterView.OnItemClickListener {

    List<BackendlessUser> userList;
    ListView lstUser;
    RoleListAdapter adapterRole;
    AlertDialog progressDailog;
    String userId;
    Dialog dialog;

    RadioButton rbAdmin,rbParamedic, rbNone;
    Button btnCancel, btnSaveNewRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        userList = new ArrayList<>();
        lstUser = findViewById(R.id.roleList);
        lstUser.setOnItemClickListener(this);
        getUsers();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Roles");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.role_change);

        rbAdmin = dialog.findViewById(R.id.rbAdmin);
        rbParamedic = dialog.findViewById(R.id.rbParamedic);
        rbNone = dialog.findViewById(R.id.rbNone);
        btnCancel = dialog.findViewById(R.id.btnCancelRole);
        btnSaveNewRole = dialog.findViewById(R.id.btnSaveNewRole);

        userId = userList.get(position).getUserId();

        if(userList.get(position).getProperty("role").equals("Admin"))
        {
            rbAdmin.setChecked(true);
            rbNone.setChecked(false);
            rbParamedic.setChecked(false);
        }
        else if(userList.get(position).getProperty("role").equals("Medic"))
        {
            rbParamedic.setChecked(true);
            rbNone.setChecked(false);
            rbAdmin.setChecked(false);
        }
        else
        {
            rbNone.setChecked(true);
            rbAdmin.setChecked(false);
            rbParamedic.setChecked(false);
        }


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSaveNewRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDailog = new SpotsDialog.Builder().setContext(Role.this).setTheme(R.style.Custom)
                        .build();
                progressDailog.show();

                Backendless.Persistence.of(BackendlessUser.class).findById(userId, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {

                        String newRole;

                        if(rbAdmin.isChecked())
                        {
                            newRole = "Admin";
                        }
                        else if(rbParamedic.isChecked())
                        {
                            newRole = "Medic";
                        }
                        else
                        {
                            newRole = "none";
                        }
                        response.setProperty("role", newRole);

                        Backendless.UserService.update(response, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                progressDailog.dismiss();
                                Role.this.finish();
                                Toast.makeText(Role.this, "Role Updated successfully!!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                progressDailog.dismiss();
                                Toast.makeText(Role.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(Role.this, "error "+ fault.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void getUsers()
    {
        progressDailog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom)
                .build();
        progressDailog.show();

        Backendless.Persistence.of(BackendlessUser.class).find(new AsyncCallback<List<BackendlessUser>>() {
            @Override
            public void handleResponse(List<BackendlessUser> response) {
                userList.clear();
                userList = response;
                adapterRole = new RoleListAdapter(Role.this, userList);
                lstUser.setAdapter(adapterRole);
                progressDailog.dismiss();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                progressDailog.dismiss();
                Toast.makeText(Role.this, "error " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

