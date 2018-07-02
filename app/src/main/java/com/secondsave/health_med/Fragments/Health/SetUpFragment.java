package com.secondsave.health_med.Fragments.Health;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.secondsave.health_med.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;


public class SetUpFragment extends Fragment implements Step {
    Spinner metrics_spinner;
    TextView weight_type,height_type;
    EditText birthday,height,weight;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_health_set_up, container, false);

        //initialize your UI
        birthday = v.findViewById(R.id.birthday_input);
        weight=v.findViewById(R.id.weight_input);
        height= v.findViewById(R.id.height_input);
        height_type = v.findViewById(R.id.height_type);
        weight_type = v.findViewById(R.id.weight_type);
        metrics_spinner = (Spinner) v.findViewById(R.id.spinner_metrics);
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

}