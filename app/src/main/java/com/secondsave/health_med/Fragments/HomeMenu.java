package com.secondsave.health_med.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.secondsave.health_med.Fragments.Health.HealthFragment;
import com.secondsave.health_med.Fragments.Pharmacy.PharmacyFragment;
import com.secondsave.health_med.Fragments.Reminders.RemindersFragment;
import com.secondsave.health_med.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMenu extends Fragment implements View.OnClickListener {

    CardView reminders, pharmacies, health, profile, settings;

    public HomeMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_menu, container, false);
        reminders = v.findViewById(R.id.remindersid);
        pharmacies = v.findViewById(R.id.pharmaciesid);
        health = v.findViewById(R.id.healthid);
//        profile = v.findViewById(R.id.profileid);
//        settings = v.findViewById(R.id.settingsid);

        reminders.setOnClickListener(this);
        pharmacies.setOnClickListener(this);
        health.setOnClickListener(this);
//        profile.setOnClickListener(this);
//        settings.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (v == reminders) {
            fragment = new RemindersFragment();
            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
        }
        if (v == pharmacies) {
            fragment = new PharmacyFragment();
            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
        }
        if (v == health) {
            fragment = new HealthFragment();
            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
        }
//        if (v == profile) {
//            fragment = new ProfileFragment();
//            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
//        }
//        if (v == settings) {
////            fragment=new SettingsFragment();
////            transaction.addToBackStack(null).replace(R.id.fragment_home_menu,fragment).commit();
//        }
    }
}
