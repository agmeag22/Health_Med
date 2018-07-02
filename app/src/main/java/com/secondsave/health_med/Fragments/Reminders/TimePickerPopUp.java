package com.secondsave.health_med.Fragments.Reminders;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.secondsave.health_med.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerPopUp extends DialogFragment implements View.OnClickListener {
    Button done;
    TimePicker timePicker;
    Calendar calendar;
    String time;

    public TimePickerPopUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.timepickerpopup, container, false);
        timePicker = v.findViewById(R.id.timepicker);
        done = v.findViewById(R.id.done);
        Bundle bundle = this.getArguments();
        if (bundle != null) {

        }
        done.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {

        if (Build.VERSION.SDK_INT < 23) {
            int getHour = timePicker.getCurrentHour();
            int getMinute = timePicker.getCurrentMinute();
            time = getHour + ":" + getMinute;
        } else {
            int getHour = timePicker.getHour();
            int getMinute = timePicker.getMinute();
            time = getHour + ":" + getMinute;
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getInt("clicked") == 3) {
                bundle.putString("time_dose", time);
            }
            Fragment fragment = new AlarmFormulary();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_reminders, fragment).commit();
        }
        this.dismiss();

    }


}
