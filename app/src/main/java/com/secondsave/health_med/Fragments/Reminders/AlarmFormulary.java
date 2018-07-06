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
import android.util.Log;
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
import com.secondsave.health_med.Database.Entities.Reminder;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;

import java.text.DateFormat;
import java.text.ParseException;
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
    String medname = null;
    String dosefrom = null;
    String doseto = null;
    int dosetype;
    String dosequantity = null;
    String timedose;
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
                dosetype = adapterView.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dose_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                if(!dose_to.getText().toString().equals("")) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date to = df.parse(dose_to.getText().toString());
                        c.setTime(to);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
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
                Calendar c = Calendar.getInstance();
                if(!dose_from.getText().toString().equals("")) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        Date from = df.parse(dose_from.getText().toString());
                        c.setTime(from);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

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
                if(! time_between_dose.getText().toString().equals("")) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("hh:mm");
                        Date from = df.parse( time_between_dose.getText().toString());
                        c.setTime(from);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                timepicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time_between_dose.setText(hourOfDay+":"+minute);
                    }
                }, mHour, mMinute, true);
                timepicker.show();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingDose();
                if(medname==null || medname.equals(""))medication_name.setError(getString(R.string.empty_error));
                if(dosefrom==null || dosefrom.equals(""))dose_from.setError(getString(R.string.empty_error));
                if(doseto==null || doseto.equals(""))dose_to.setError(getString(R.string.empty_error));
                if(doseto==null || doseto.equals(""))dose_to.setError(getString(R.string.empty_error));
                if(dosequantity==null || dosequantity.equals(""))dose_quantity.setError(getString(R.string.empty_error));
                if(timedose==null || timedose.equals(""))time_between_dose.setError(getString(R.string.empty_error));
                if(     !(medname==null || medname.equals("")) &&
                        !(dosefrom==null || dosefrom.equals("")) &&
                        !(doseto==null || doseto.equals("")) &&
                        !(doseto==null || doseto.equals("")) &&
                        !(dosequantity==null || dosequantity.equals(""))&&
                        !(timedose==null || timedose.equals(""))&&
                        !((medname==null || medname.equals("")))
                        ) {
                    doInBackGround task = new doInBackGround();
                    task.execute();
                }

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
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (bundle.getString("med_name") != null) {
            medication_name.setText(bundle.getString("med_name"));
        }
        if (bundle.getSerializable("dose_from") != null) {

            dose_from.setText(df.format((Date)bundle.getSerializable("dose_from")));
        }
        if (bundle.getSerializable("dose_to") != null) {
            dose_to.setText(df.format((Date)bundle.getSerializable("dose_to")));
        }
        if (bundle.getFloat("dose_quantity",0) > 0) {
            dose_quantity.setText(bundle.getFloat("dose_quantity")+"");
        }
        if (bundle.getFloat("time_dose") > 0) {
            float time=bundle.getFloat("time_dose");
            int hours = (int)time;
            int mins =  (int)((time - hours) * 60);
            String smins=mins+"",shours=hours+"";
            if(mins<10)
                smins="0" + mins;
            if(hours<10){
                shours="0"+hours;
            }
            time_between_dose.setText(shours+":"+smins);
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

            try {

                float size = Float.parseFloat(dosequantity);
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Date from = df.parse(dosefrom);
                Date to = df.parse(doseto);
                String td[] = timedose.split(":");
                Float lapse = Float.parseFloat(td[0])+(Float.parseFloat(td[0])/60);
                Dose dose = new Dose(user, dosetype, medname, size,from,to,lapse, true);
                healthMedViewModel.insertDose(dose);
                return R.string.sucess;
            }catch (Exception e){
                Log.e("ERROR EN GUARDAR DOSE", "doInBackground: "+e.getMessage().toString() );
                return R.string.error;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Snackbar.make(getView(),integer,Snackbar.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }

    public void setValues(Dose d){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        if (d.getName() != null) {
            medication_name.setText(d.getName());
        }
        if (d.getStart_date() != null) {

            dose_from.setText(df.format(d.getStart_date()));
        }
        if (d.getEnd_date() != null) {
            dose_to.setText(df.format(d.getEnd_date()));
        }
        if (d.getSize()>0) {
            dose_quantity.setText(d.getSize()+"");
        }
        if (d.getLapse()>0) {
            time_between_dose.setText(d.getLapse()+"");
        }
        dose_type.setSelection(d.getId_dose_type());
    }
}
