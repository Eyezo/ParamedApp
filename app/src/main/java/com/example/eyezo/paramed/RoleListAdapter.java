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

public class RoleListAdapter extends ArrayAdapter<BackendlessUser> {

    private Context context;
    private List<BackendlessUser> userList;

    public RoleListAdapter(Context context, List<BackendlessUser> userList)
    {
        super(context, R.layout.role_row, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflator.inflate(R.layout.role_row,parent,false);

        TextView userName = row.findViewById(R.id.userNames);
        TextView userRole = row.findViewById(R.id.userRole);

        userName.setText("Names: " +(String)userList.get(position).getProperty("name"));
        userRole.setText("Role: " +(String)userList.get(position).getProperty("role"));

        return row;
    }
}
