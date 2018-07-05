package com.secondsave.health_med.Fragments.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.secondsave.health_med.Activities.MainActivity;
import com.secondsave.health_med.Adapters.IMCAdapter;
import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.Fragments.Reminders.Alarms;
import com.secondsave.health_med.R;

import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class RemindersFragment extends Fragment {
    EditText etVal;
    Button btnSet;
    TextView hola;
    FloatingActionButton addBtn;
    RelativeLayout relativeLayout;
    private HealthMedViewModel mhealthmedViewModel;
    private SharedPreferences prefs;
    private String user;
    private RecyclerView recycler;
    
    public RemindersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_reminders, container, false);
        //etVal =v.findViewById(R.id.text);
        // btnSet =v.findViewById(R.id.button);
        relativeLayout = v.findViewById(R.id.fragment_reminders);
        addBtn = v.findViewById(R.id.addbtn);

        mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
        recycler = v.findViewById(R.id.recyclerReminders);
        adapter = new IMCAdapter(null);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        LiveData<List<IMCEntry>> list = mhealthmedViewModel.getAllValuesByUsername(user);
        list.observe(this, new Observer<List<IMCEntry>>() {
            @Override
            public void onChanged(@Nullable List<IMCEntry> imcEntries) {
                adapter.setList(imcEntries);
                adapter.notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.GONE);
                Fragment fragment = new AlarmFormulary();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack("alarmformulary").replace(R.id.fragment_reminders, fragment).commit();

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.VISIBLE);
            }
        });

//        btnSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int time = Integer.parseInt(etVal.getText().toString());
//                Intent intent=new Intent(getContext(), Alarms.class);
//                PendingIntent p1=PendingIntent.getBroadcast(getContext(),0, intent,0);
//                AlarmManager a=(AlarmManager) getContext().getSystemService(ALARM_SERVICE);
//                a.set(AlarmManager.RTC,System.currentTimeMillis() + time*1000,p1);
//                Toast.makeText(getContext(),"Alarm set in "+time+"seconds",Toast.LENGTH_LONG).show();
//            }
//            });
        return v;
    }


}