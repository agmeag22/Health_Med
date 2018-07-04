package com.secondsave.health_med.Fragments.Health;


import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.secondsave.health_med.Utils.Gender;
import com.secondsave.health_med.Utils.IMC;
import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.Database.Entities.PersonalInfo;
import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class SetUpFragment extends Fragment implements Step,View.OnClickListener {
    Spinner metrics_spinner;
    TextView weight_type,height_type;
    EditText birthday,height,weight;
    RadioGroup sex;
    RadioButton female,male;
    int sex_value=0;
    Button save;
    private DatePickerDialog datepicker;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_health_set_up, container, false);

        //initialize your UI
        birthday = v.findViewById(R.id.birthday_input);
        weight=v.findViewById(R.id.weight_input);
        height= v.findViewById(R.id.height_input);
        height_type = v.findViewById(R.id.height_type);
        weight_type = v.findViewById(R.id.weight_type);
        metrics_spinner = (Spinner) v.findViewById(R.id.spinner_metrics);
        sex = v.findViewById(R.id.sex_radio_group);
        save = v.findViewById(R.id.save_button);
        female = v.findViewById(R.id.female);
        male = v.findViewById(R.id.male);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.metrics_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        metrics_spinner.setAdapter(adapter);
        metrics_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){

                    height_type.setText("CM");
                    weight_type.setText("KG");
                }else if(position==1){
                    height_type.setText("IN");
                    weight_type.setText("LB");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                height_type.setText("IN");
                weight_type.setText("LB");
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthday.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                }, year, month, day);
                datepicker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(birthday.getText().toString().equals(""))birthday.setError(getString(R.string.empty_field));
                if(height.getText().toString().equals(""))height.setError(getString(R.string.empty_field));
                if(weight.getText().toString().equals(""))weight.setError(getString(R.string.empty_field));
                if(!birthday.getText().toString().equals("") && !height.getText().toString().equals("") && !weight.getText().toString().equals("") && sex_value>0) {
                    saveUserInfoTask task = new saveUserInfoTask();
                    task.execute();
                }

            }
        });

        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId==R.id.male){
//                        Toast.makeText(getContext(), "SELECCIONO HOMBRE", Toast.LENGTH_SHORT).show();
                        male.setBackgroundResource(R.drawable.man_selected);
                        female.setBackgroundResource(R.drawable.woman);
                        sex_value = Gender.MALE;

                    }else if(checkedId==R.id.female){
//                        Toast.makeText(getContext(), "SELECCIONO MUJER", Toast.LENGTH_SHORT).show();
                        male.setBackgroundResource(R.drawable.man);
                        female.setBackgroundResource(R.drawable.woman_selected);
                        sex_value = Gender.FEMALE;
                    }
            }
        });
        return v;
    }

    @Override
    public VerificationError verifyStep() {
        //return null if the user can go to the next step, create a new VerificationError instance otherwise
        return null;
    }

    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        //handle error inside of the fragment, e.g. show error on EditText
    }

    @Override
    public void onClick(View v) {

    }

    public class saveUserInfoTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            SharedPreferences prefs = getContext().getSharedPreferences(
                    "com.secondsave.health_med", getContext().MODE_PRIVATE);
            String user = prefs.getString("username","");
            HealthMedViewModel mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
            User u = mhealthmedViewModel.getUserByUsernameAsync(user);
            if(u!=null){
                PersonalInfo personalInfo = mhealthmedViewModel.getPersonalInfoAsync(u);
                if(personalInfo!=null){
                    personalInfo.setGender(sex_value);
                    String bday = birthday.getText().toString();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date parsed = format.parse(bday);
                        java.sql.Date sql = new java.sql.Date(parsed.getTime());
                        personalInfo.setBirth(sql);
                        mhealthmedViewModel.updatePersonaInfo(personalInfo);
                        Float w,h,imc;
                        if(metrics_spinner.getSelectedItemPosition()==0){
                            h = Float.parseFloat(height.getText().toString())/100;
                            w = Float.parseFloat(weight.getText().toString());
                            imc = IMC.Calculate(w,h);
                        }else{
                            h = Float.parseFloat(height.getText().toString());
                            w = Float.parseFloat(weight.getText().toString());
                            imc = IMC.Calculate(w,h)*703;
                        }

                            long timestamp = Calendar.getInstance().getTimeInMillis();

                            IMCEntry imcEntry= new IMCEntry(u.getUsername(), imc, h,w,new java.sql.Date(timestamp));
                            mhealthmedViewModel.insertValues(imcEntry);
                            Log.d("IMC", "doInBackground: "+ imc);
//                            List<IMCEntry> l = mhealthmedViewModel.getAllValuesByUsername(u.getUsername()).getValue();

                        return R.string.sucess;

                    } catch (ParseException e) {

                        e.printStackTrace();
                        return R.string.error;

                    }

//                    personalInfo.setBirth();


                }
            }

            return R.string.error;
        }

        @Override
        protected void onPostExecute(Integer a) {
            if(a==R.string.sucess){
                getFragmentManager().popBackStack();
            }
                Snackbar.make(v, a, Snackbar.LENGTH_SHORT).show();
        }
    }
}