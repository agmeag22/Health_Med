package com.secondsave.health_med.Fragments.Health;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.Database.Entities.PersonalInfo;
import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;
import com.secondsave.health_med.Utils.Gender;
import com.secondsave.health_med.Utils.IMC;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class NewEntryDialog extends DialogFragment {


    Spinner metrics_spinner;
    TextView weight_type, height_type;
    EditText date, weight;
    private HealthMedViewModel mhealthmedViewModel;
    private SharedPreferences prefs;
    private String user;
    private View v;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
        prefs = getContext().getSharedPreferences(
                "com.secondsave.health_med", getContext().MODE_PRIVATE);
        user = prefs.getString("username", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.fragment_new_entry, null);
        searchViews(v);
        builder.setView(v);
        builder.setMessage(R.string.new_entry_msg)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (date.getText().toString().equals(""))
                            date.setError(getString(R.string.empty_field));
//                        if (height.getText().toString().equals(""))
//                            height.setError(getString(R.string.empty_field));
                        if (weight.getText().toString().equals(""))
                            weight.setError(getString(R.string.empty_field));
                        if (!date.getText().toString().equals("") &&  !weight.getText().toString().equals("")) {
                            doInBackGround task = new doInBackGround();
                            task.execute();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    public void searchViews(View v) {
        date = v.findViewById(R.id.date_input);
        weight = v.findViewById(R.id.weight_input);
//        height = v.findViewById(R.id.height_input);
//        height_type = v.findViewById(R.id.height_type);
        weight_type = v.findViewById(R.id.weight_type);
        metrics_spinner = (Spinner) v.findViewById(R.id.spinner_metrics);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.metrics_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metrics_spinner.setAdapter(adapter);
        metrics_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
//                    height_type.setText("CM");
                    weight_type.setText("KG");
                } else if (position == 1) {
//                    height_type.setText("IN");
                    weight_type.setText("LB");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                height_type.setText("IN");
                weight_type.setText("LB");
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

    }

    public class doInBackGround extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {

            User u = mhealthmedViewModel.getUserByUsernameAsync(user);
            if (user != "") {
                PersonalInfo info = mhealthmedViewModel.getPersonalInfoAsync(u);
                float h=info.getHeight();
                Float w, imc;
                if (metrics_spinner.getSelectedItemPosition() == 0) {
//                    h = Float.parseFloat(height.getText().toString()) / 100;
                    w = Float.parseFloat(weight.getText().toString());
                    imc = IMC.Calculate(w, h);
                } else {
//                    h = Float.parseFloat(height.getText().toString());
                    w = Float.parseFloat(weight.getText().toString());
                    w = (float)(w * 0.453592);
                    imc = IMC.Calculate(w, h);

                }

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    String string = date.getText().toString();


                    Date parsed = format.parse(string);
                    Calendar c = Calendar.getInstance();
                    c.setTime(info.getBirth());
                    int age = getAge(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
                    int gender = info.getGender()== Gender.MALE?1:0;
                    int fat =(int) Math.round((1.39 * imc) + (0.16 * age) - (10.34 * gender) - 9);
                    IMCEntry imcEntry = new IMCEntry(user, imc, fat, w, parsed);
                    mhealthmedViewModel.insertValues(imcEntry);
                    return R.id.sucess;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return R.id.error;
                }
            }
           return R.id.error;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Snackbar.make(v,integer,Snackbar.LENGTH_SHORT).show();
        }
    }

    public static int getAge (int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                || ((m == cal.get(Calendar.MONTH)) && (d < cal
                .get(Calendar.DAY_OF_MONTH)))) {
            --a;
        }
        if(a < 0)
            throw new IllegalArgumentException("Age < 0");
        return a;
    }
}
