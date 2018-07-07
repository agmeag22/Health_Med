package com.secondsave.health_med.Fragments.Pharmacy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.secondsave.health_med.Adapters.PlacesAdapter;
import com.secondsave.health_med.R;
import java.util.ArrayList;


public class PharmacyFragment extends Fragment {

    private static final int CALL_REQ_CODE = 2;
    private OnFragmentInteractionListener mListener;
    protected PlaceDetectionClient placeDetectionClient;
    protected GeoDataClient geoDataClient;
    private View v;
    public static final String TAG = "CurrentLocNearByPlaces";
    private static final int LOC_REQ_CODE = 1;
    private RecyclerView recyclerView;
    private View error_view;
    private TextView message;
    private MenuItem view_map;
    private ArrayList<Place> placesList;

    public PharmacyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_pharmacy, container, false);

        recyclerView = v.findViewById(R.id.recycler);
        error_view = v.findViewById(R.id.no_pharmacies);
        message = v.findViewById(R.id.message);
        setHasOptionsMenu(true);
        placeDetectionClient = Places.getPlaceDetectionClient(getActivity());
        getCurrentPlaceItems();
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pharmacy_menu, menu);
        view_map = menu.findItem(R.id.action_view_map);
        view_map.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(placesList!=null) {
                    MapFragment fragment = new MapFragment();
                    fragment.updateList(placesList);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void getCurrentPlaceItems() {
        if (isLocationAccessPermitted()) {
            getCurrentPlaceData();
        } else {
            requestLocationAccessPermission();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentPlaceData() {
//        try {
//            int locationMode = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
                Task<PlaceLikelihoodBufferResponse> placeResult = placeDetectionClient.getCurrentPlace(null);
                placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                        if (isLocationEnabled(getContext())) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                                placesList = new ArrayList<>();
                                PlacesAdapter recyclerViewAdapter = new PlacesAdapter(placesList) {
                                    @Override
                                    protected void locationOnClick(Place place) {
                                        Log.d(TAG, "locationOnClick: "+ "geo:"+place.getLatLng().latitude+","+place.getLatLng().longitude);
                                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+place.getLatLng().latitude+","+place.getLatLng().longitude);
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        Snackbar.make(v,getString(R.string.no_gmaps),Snackbar.LENGTH_SHORT).show();
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                            startActivity(mapIntent);
                                        }else{
                                            Snackbar.make(v,getString(R.string.no_gmaps),Snackbar.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void phoneOnClick(Place place) {
                                        if(isCallPhonePermitted()) {
                                            Intent intent = new Intent(Intent.ACTION_CALL);
                                            Log.d(TAG, "phoneOnClick: "+ place.getPhoneNumber());
                                            intent.setData(Uri.parse("tel:" + place.getPhoneNumber()));
                                            startActivity(intent);
                                        }else{
                                            requestCallPhonePermission();
                                        }
                                    }
                                };
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(recyclerViewAdapter);
                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                                    Place place = placeLikelihood.getPlace();
                                    if (place.getPlaceTypes().contains(Place.TYPE_PHARMACY)) {
                                        Log.i(TAG, String.format("Place '%s' has likelihood: %g",
                                                place.getName(),
                                                placeLikelihood.getLikelihood()));
                                        placesList.add(placeLikelihood.getPlace().freeze());
                                        recyclerViewAdapter.notifyItemInserted(placesList.size() - 1);
                                    }
                                }
                                if (placesList.size() == 0) {
                                    recyclerView.setVisibility(View.GONE);
                                    error_view.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    error_view.setVisibility(View.GONE);
                                }
                                likelyPlaces.release();
                            } else {
                                recyclerView.setVisibility(View.GONE);
                                error_view.setVisibility(View.VISIBLE);
                                message.setText(R.string.enable_high_accuracy);
                            }
                        }else {
                            recyclerView.setVisibility(View.GONE);
                            error_view.setVisibility(View.VISIBLE);
                            message.setText(R.string.enable_location);
                        }
                    }
                });


    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private boolean isLocationAccessPermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    private void requestLocationAccessPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOC_REQ_CODE);
    }


    private boolean isCallPhonePermitted() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    private void requestCallPhonePermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CALL_PHONE},
                CALL_REQ_CODE);
    }

}
