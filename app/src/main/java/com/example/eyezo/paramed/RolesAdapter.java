package com.example.eyezo.paramed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.BackendlessUser;

import java.util.List;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.ViewHolder> {

    private List<BackendlessUser> players;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public RolesAdapter(Context context, List<BackendlessUser> players)
    {
        this.players = players;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name, userRole;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.userNames);
            userRole = itemView.findViewById(R.id.userRole);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(players.indexOf((BackendlessUser) v.getTag()));
                }
            });

        }
    }

    @Override
    public RolesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.role_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RolesAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(players.get(position));
        holder.name.setText("Name : " + (String)players.get(position).getProperty("name"));
        holder.userRole.setText("Role: " + (String)players.get(position).getProperty("role"));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
