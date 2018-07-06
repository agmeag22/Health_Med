package com.secondsave.health_med.Fragments.Reminders;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.secondsave.health_med.Database.Entities.Dose;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class AlarmFormulary extends Fragment {
    EditText medication_name;
    EditText dose_from;
    EditText dose_to;
    EditText time_between_dose;
    Spinner dose_type;
    EditText dose_quantity;
    Button start;
    String medname = null, dosefrom = null, doseto = null, dosetype = null, dosequantity = null, timedose;
    Date dosefromdate = null, dosetodate = null;
    int dosetypeint = 0;
    float dosequantityfloat = 0, timedosefloat = 0;
    HealthMedViewModel healthMedViewModel;
    private SharedPreferences prefs;
    private String user;
    private DatePickerDialog datepicker;
    private TimePickerDialog timepicker;
    //Day buttons

    public AlarmFormulary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarmformulary, container, false);
        healthMedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
        prefs = getContext().getSharedPreferences(
                "com.secondsave.health_med", getContext().MODE_PRIVATE);
        user = prefs.getString("username", "");
        medication_name = v.findViewById(R.id.text_med_name);
        dose_from = v.findViewById(R.id.from_this_day);
        dose_to = v.findViewById(R.id.tot_this_day);
        dose_type = v.findViewById(R.id.dose_type_selection);
        dose_quantity = v.findViewById(R.id.dose_quantity);
        start = v.findViewById(R.id.start);
        time_between_dose = v.findViewById(R.id.timepicker_edit_text);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.medication_measurement, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dose_type.setAdapter(adapter);


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

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dose_to.setText(dayOfMonth + "/" + (month +1) + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

        dose_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dose_from.setText(dayOfMonth + "/" + (month +1) + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });


        time_between_dose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time_between_dose.setText(hourOfDay+":"+minute);
                    }
                }, mHour, mMinute, false);
                timepicker.show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            doInBackGround task = new doInBackGround();
            task.execute();

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

    public void settingDose() {

        if (medication_name.getText() != null && !medication_name.getText().equals("")) {
            medname = medication_name.getText().toString();
        }
        if (dose_from.getText() != null && !dose_from.getText().equals("")) {
            dosefrom = dose_from.getText().toString();
        }
        if (dose_to.getText() != null && !dose_to.getText().equals("")) {
            doseto = dose_to.getText().toString();
        }
        if (dose_quantity.getText() != null && !dose_quantity.getText().equals("")) {
            dosequantity = dose_quantity.getText().toString();
        }
        if (time_between_dose.getText() != null && !time_between_dose.getText().equals("")) {
            timedose = time_between_dose.getText().toString();
        }
    }

    public class doInBackGround extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
            settingDose();
            try {
                int dt = Integer.parseInt(dosetype);
                float size = Float.parseFloat(dosequantity);
                SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                Date from = df.parse(dosefrom);
                Date to = df.parse(doseto);
                Float lapse = Float.parseFloat(timedose);
                Dose dose = new Dose(user, dt, medname, size,from,to,lapse, true);
                healthMedViewModel.insertDose(dose);
                return R.string.sucess;
            }catch (Exception e){
                return R.string.error;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Snackbar.make(getView(),integer,Snackbar.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }
}
