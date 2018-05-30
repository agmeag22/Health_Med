package com.secondsave.health_med.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;
import com.secondsave.health_med.R;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.ViewHolder> {
    View v;
    private List<Place> places;


    public PlacesAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = parent.inflate(parent.getContext(),R.layout.item_place_list,null);
        ViewHolder vh= new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(places !=null) {
            holder.name.setText(places.get(position).getName());
            holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
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
        TextView name;
        ImageView location,phone;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.place_name_txt);
            location = itemView.findViewById(R.id.location_icon);
            phone = itemView.findViewById(R.id.phone_icon);
        }

    }
}
