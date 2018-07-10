package com.secondsave.health_med.Fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.secondsave.health_med.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEdit extends Fragment {

    EditText name, lastname, birthday, height;
    de.hdodenhof.circleimageview.CircleImageView imageProfile;
    RadioButton male, female;
    FloatingActionButton changepicture;

    public ProfileEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        name = v.findViewById(R.id.first_name);
        lastname = v.findViewById(R.id.last_name);
        birthday = v.findViewById(R.id.birthday_text);
        height = v.findViewById(R.id.height_text);
        imageProfile = v.findViewById(R.id.profile_image);
        male = v.findViewById(R.id.male);
        female = v.findViewById(R.id.female);
        changepicture = v.findViewById(R.id.floatingActionButton);


        return v;
    }


}
