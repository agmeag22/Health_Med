package com.secondsave.health_med.Fragments.Reminders;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.secondsave.health_med.R;


public class AlarmFormulary extends Fragment {
    EditText medication_name;
    EditText dose_from;
    EditText dose_to;
    EditText time_between_dose;
    Spinner dose_type;
    EditText dose_quantity;
    Button start;
    String medname = null, dosefrom = null, doseto = null, dosetype = null, dosequantity = null, timedose;

    //Day buttons

    public AlarmFormulary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarmformulary, container, false);
        medication_name = v.findViewById(R.id.text_med_name);
        dose_from = v.findViewById(R.id.from_this_day);
        dose_to = v.findViewById(R.id.tot_this_day);
        dose_type = v.findViewById(R.id.dose_type_selection);
        dose_quantity = v.findViewById(R.id.dose_quantity);
        start = v.findViewById(R.id.start);
        time_between_dose = v.findViewById(R.id.timepicker_edit_text);
        String[] letra = {"Pill", "Tablespoon", "Cup", "ml"};
        dose_type.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, letra));

        Bundle bundle = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            gettingValues(bundle);
        }
        dose_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                dosetype = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dose_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DialogFragment datePickerPopUp2 = new TimePickerPopUp();
                Bundle args = new Bundle();
                args.putInt("clicked", 2);
                datePickerPopUp2.setArguments(settingValues(args));
                datePickerPopUp2.show(transaction, null);
            }
        });

        dose_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DialogFragment datePickerPopUp1 = new DatePickerPopUp();
                Bundle args = new Bundle();
                args.putInt("clicked", 1);
                datePickerPopUp1.setArguments(settingValues(args));
                datePickerPopUp1.show(transaction, null);
            }
        });


        time_between_dose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DialogFragment timePickerPopUp = new TimePickerPopUp();
                Bundle args = new Bundle();
                args.putInt("clicked", 3);
                timePickerPopUp.setArguments(settingValues(args));
                timePickerPopUp.show(transaction, null);
            }
        });
        return v;
    }


    public Bundle settingValues(Bundle args) {

        if (medication_name.getText() != null && !medication_name.getText().equals("")) {
            medname = medication_name.getText().toString();
            args.putString("med_name", medname);
        }
        if (dose_from.getText() != null && !dose_from.getText().equals("")) {
            dosefrom = dose_from.getText().toString();
            args.putString("dose_from", dosefrom);
        }
        if (dose_to.getText() != null && !dose_to.getText().equals("")) {
            doseto = dose_to.getText().toString();
            args.putString("dose_to", doseto);
        }
        if (dose_quantity.getText() != null && !dose_quantity.getText().equals("")) {
            dosequantity = dose_quantity.getText().toString();
            args.putString("dose_quantity", dosequantity);
        }
        if (time_between_dose.getText() != null && !time_between_dose.getText().equals("")) {
            timedose = time_between_dose.getText().toString();
            args.putString("time_dose", timedose);
        }

        return args;
    }

    public void gettingValues(Bundle bundle) {
        if (bundle.getString("med_name") != null) {
            medication_name.setText(bundle.getString("med_name"));
        }
        if (bundle.getString("dose_from") != null) {
            dose_from.setText(bundle.getString("dose_from"));
        }
        if (bundle.getString("dose_to") != null) {
            dose_to.setText(bundle.getString("dose_to"));
        }
        if (bundle.getString("dose_quantity") != null) {
            dose_quantity.setText(bundle.getString("dose_quantity"));
        }
        if (bundle.getString("time_dose") != null) {
            time_between_dose.setText(bundle.getString("time_dose"));
        }
    }
}
