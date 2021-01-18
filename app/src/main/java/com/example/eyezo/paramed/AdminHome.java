package com.example.eyezo.paramed;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import dmax.dialog.SpotsDialog;

public class AdminHome extends AppCompatActivity {

    Dialog dlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Admin");
        actionBar.setIcon(R.mipmap.ic_launcher);
    }
    public void btnRolesSelect(View v)
    {
        startActivity(new Intent(this, Role.class));
    }
    public void btnViewInc(View v)
    {
        startActivity(new Intent(this, ViewIncident.class));
    }
    public void btnViewPat(View view){
        startActivity(new Intent(this, ViewPatient.class));
    }
    @Override
    public void onBackPressed() {



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {
            dlg = new SpotsDialog.Builder().setTheme(R.style.Custom).setContext(this).build();
            dlg.show();

            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void response) {
                    startActivity(new Intent(AdminHome.this, MainActivity.class));
                    AdminHome.this.finish();
                    dlg.dismiss();
                    Toast.makeText(AdminHome.this, "User Successfully logged out", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleFault(BackendlessFault fault) {
                    Toast.makeText(AdminHome.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
