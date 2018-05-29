package com.secondsave.health_med.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.secondsave.health_med.R;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    View v;
    private List<Place> places;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = parent.inflate(parent.getContext(),R.layout.element_user,null);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(places !=null) {
            holder.username.setText(places.get(position).getName());
            holder.password.setText(places.get(position).getPhoneNumber());
        }
    }

    @Override
    public int getItemCount() {
        if (places != null)
            return places.size();
        else return 0;
    }

    public void setList(List<Place> mUsers) {
        this.places = mUsers;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,password;
        public ViewHolder(View itemView) {
            super(itemView);
            password = itemView.findViewById(R.id.passwort_txt);
            username = itemView.findViewById(R.id.username_txt);
        }

    }
}
