package com.secondsave.health_med.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.secondsave.health_med.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEdit extends Fragment {


    public ProfileEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);

        return v;
    }

}
