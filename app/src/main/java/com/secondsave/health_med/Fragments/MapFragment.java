package com.secondsave.health_med.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.secondsave.health_med.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Place> places;
    private List<Marker> markers;


//    public static MapFragment NewInstance(List<Place> places){
//        MapFragment fragment = new MapFragment();
//        Bundle bundle = new Bundle();
//        Gson gson = new Gson();
//        String element = gson.toJson(
//                places,
//                new TypeToken<ArrayList<Place>>() {}.getType());
//        bundle.putString("places",element);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,
                container, false);

//        if(getArguments() != null){
//            String json_places = getArguments().getString("places");
//            Gson gson = new Gson();
//            Type placesListType = new TypeToken<ArrayList<Place>>(){}.getType();
//            places = gson.fromJson(json_places, placesListType);
//        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gmap);
        mapFragment.getMapAsync(this);

        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
        markers = new ArrayList();
        for(Place place:places) {
            LatLng placeLoc = place.getLatLng();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(placeLoc)
                    .title(place.getName().toString())
                    .snippet(place.getAddress().toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            Marker m = mMap.addMarker(markerOptions);
            markers.add(m);
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds;
        if(markers.size()>0) {
            bounds = builder.build();
            int padding = 0; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }


    }

    public void updateList(List<Place> places){
       this.places = places;
    }

}
