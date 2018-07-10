package com.secondsave.health_med.Fragments.Reminders;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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


public class AlarmFormulary extends Fragment implements View.OnClickListener {
    EditText medication_name;
    EditText dose_from;
    EditText dose_to;
    EditText time_between_dose;
    EditText dose_quantity;
    ImageView start;
    String medname = null;
    String dosefrom = null;
    String doseto = null;
    int dosetype=0;
    String dosequantity = null;
    String timedose;
    HealthMedViewModel healthMedViewModel;
    private SharedPreferences prefs;
    private String user;
    private DatePickerDialog datepicker;
    private TimePickerDialog timepicker;
    ImageView pill,tablespoon,ml,cup;
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
        dose_quantity = v.findViewById(R.id.dose_quantity);
        start = v.findViewById(R.id.start);
        time_between_dose = v.findViewById(R.id.timepicker_edit_text);
        pill=v.findViewById(R.id.pill);
        tablespoon=v.findViewById(R.id.tablespoon);
        cup=v.findViewById(R.id.measurincup);
        ml=v.findViewById(R.id.mliliters);
        Bundle bundle = new Bundle();
        bundle = getArguments();
        if (bundle != null) {
            gettingValues(bundle);
        }


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

        pill.setOnClickListener(this);
        cup.setOnClickListener(this);
        tablespoon.setOnClickListener(this);
        ml.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v==pill){
            dosetype=0;
            pill.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill_selected));
            tablespoon.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablespoon));
            cup.setImageDrawable(getResources().getDrawable(R.drawable.ic_measuringcup));
            ml.setImageDrawable(getResources().getDrawable(R.drawable.ic_ml));

        }
        if(v==tablespoon){
            dosetype=1;
            tablespoon.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablespoon_selected));
            pill.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill));
            cup.setImageDrawable(getResources().getDrawable(R.drawable.ic_measuringcup));
            ml.setImageDrawable(getResources().getDrawable(R.drawable.ic_ml));
        }
        if(v==cup){
            dosetype=2;
            cup.setImageDrawable(getResources().getDrawable(R.drawable.ic_measuringcup_selected));
            tablespoon.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablespoon));
            pill.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill));
            ml.setImageDrawable(getResources().getDrawable(R.drawable.ic_ml));
        }
        if(v==ml){
            dosetype=3;
            ml.setImageDrawable(getResources().getDrawable(R.drawable.ic_ml_selected));
            tablespoon.setImageDrawable(getResources().getDrawable(R.drawable.ic_tablespoon));
            cup.setImageDrawable(getResources().getDrawable(R.drawable.ic_measuringcup));
            pill.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill));
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
                Float lapse = Float.parseFloat(td[0])+(Float.parseFloat(td[1])/60);
                Dose dose = new Dose(user, dosetype, medname, size,from,to,lapse, true);
                healthMedViewModel.insertDose(dose);
                Calendar now = Calendar.getInstance();
                if(now.getTimeInMillis()>=from.getTime()) {
                    setAlarm(now.getTime(), lapse,dose,getContext());
                }else {
                    setAlarm(from, lapse,dose,getContext());
                }
                return R.string.sucess;
            }catch (Exception e){
                Log.e("ERROR EN GUARDAR DOSE", "doInBackground: "+e.getMessage().toString() );
                return R.string.error;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
//            if(integer==R.id.sucess){
//
//            }
            Snackbar.make(getView(),integer,Snackbar.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
    }

    public static void setAlarm(Date date,float n_hours,Dose dose, Context context){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = (int)n_hours;
        int mins =  (int)((n_hours - hours) * 60);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy -- hh:mm");

        Log.d("ALARM", "setAlarm: "+df.format(cal.getTime()));
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, mins);
        Log.d("ALARM", "setAlarm: "+df.format(cal.getTime()));
        Intent intent = new Intent(context, AlarmReceiverActivity.class);
        intent.putExtra("id", dose.getId_dose());
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                1234, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am =
                (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                1000 * 60 * (mins + hours*60), pendingIntent);
    }
}
