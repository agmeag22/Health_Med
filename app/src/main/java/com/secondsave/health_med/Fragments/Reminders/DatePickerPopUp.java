package com.secondsave.health_med.Fragments.Reminders;


import android.arch.persistence.room.Transaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.secondsave.health_med.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerPopUp extends DialogFragment implements View.OnClickListener {
    Button done;
    DatePicker datePicker;
    String date;
    Calendar calendar = Calendar.getInstance();

    public DatePickerPopUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.datepickerpopup, container, false);
        datePicker = v.findViewById(R.id.timepicker);
        done = v.findViewById(R.id.done);
        done.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v) {


        if (Build.VERSION.SDK_INT < 23) {
            int getDay = datePicker.getDayOfMonth();
            int getMonth = datePicker.getMonth();
            int getYear = datePicker.getYear();
            date = getDay + "/" + getMonth + "/" + getYear;
            calendar.set(getYear, getMonth, getDay);
        } else {
            int getDay = datePicker.getDayOfMonth();
            int getMonth = datePicker.getMonth();
            int getYear = datePicker.getYear();

            calendar.set(getYear, getMonth, getDay);

        }
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getInt("dosefrom") == 1) {
                bundle.putString("dose_from", calendar.toString());
            }
            if (bundle.getInt("dosefrom") == 2) {
                bundle.putString("dose_to", calendar.toString());
            }

            Fragment fragment = new AlarmFormulary();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_reminders, fragment).commit();
        }
        this.dismiss();


    }


}
