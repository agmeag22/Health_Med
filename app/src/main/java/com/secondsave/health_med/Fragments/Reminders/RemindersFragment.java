package com.secondsave.health_med.Fragments.Reminders;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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

import com.secondsave.health_med.Adapters.ReminderAdapter;
import com.secondsave.health_med.Database.Entities.Dose;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RemindersFragment extends Fragment {
    EditText etVal;
    Button btnSet;
    TextView hola;
    FloatingActionButton addBtn;
    RelativeLayout relativeLayout;
    ReminderAdapter adapter;
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
        View v = inflater.inflate(R.layout.fragment_reminders, container, false);
        //etVal =v.findViewById(R.id.text);
        // btnSet =v.findViewById(R.id.button);
        relativeLayout = v.findViewById(R.id.fragment_reminders);
        addBtn = v.findViewById(R.id.addbtn);

        mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
        recycler = v.findViewById(R.id.recyclerReminders);
        adapter = new ReminderAdapter(null, getContext()) {
            @Override
            protected void LongClickListener(Dose dose) {
                    ShowDeleteDialog(dose);
            }

            @Override
            public void setAlarmStatus(boolean i, Dose dose) {
                if(i){
                    cancelAlarm(dose);
                    setAlarm(dose.getStart_date(),dose.getLapse(),dose);
                }else{
                    cancelAlarm(dose);
                }
                dose.setReminder_enabled(i);

                updateReminderTask updateDoseTask = new updateReminderTask(dose);
                updateDoseTask.execute();
            }

            @Override
            public void onClickItemMethod(Dose dose) {
                addBtn.setVisibility(View.GONE);
                Bundle args = new Bundle();
                args.putString("med_name", dose.getName());
                args.putSerializable("dose_from", dose.getStart_date());
                args.putSerializable("dose_to", dose.getEnd_date());
                args.putFloat("dose_quantity", dose.getSize());
                args.putFloat("time_dose", dose.getLapse());
                AlarmFormulary fragment = new AlarmFormulary();
                fragment.setArguments(args);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack("alarmformulary").replace(R.id.fragment_reminders, fragment).commit();
                //fragment.setValues(dose);
            }
        };
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        LiveData<List<Dose>> list = mhealthmedViewModel.getAllDose();
        list.observe(this, new Observer<List<Dose>>() {
            @Override
            public void onChanged(@Nullable List<Dose> doseList) {
                adapter.setList(doseList);
                adapter.notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.GONE);
                Fragment fragment = new AlarmFormulary();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_reminders, fragment).commit();

            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBtn.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    public void ShowDeleteDialog(final Dose dose){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_delete_msg)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        cancelAlarm(dose);
                        removeReminderTask task = new removeReminderTask(dose);
                        task.execute();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.show();

    }

    public class updateReminderTask extends AsyncTask<Void, Void, Integer> {
        private Dose dose;

        public updateReminderTask(Dose dose) {
            this.dose = dose;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                mhealthmedViewModel.updateDose(dose);
                return R.string.sucess;
            } catch (Exception e) {
                Log.e("ERROR", "doInBackground: " + e.getMessage());
                return R.string.error;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if(integer==R.string.error) {
                Snackbar.make(getView(), integer, Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    public class removeReminderTask extends AsyncTask<Void, Void, Integer> {
        private Dose dose;

        public removeReminderTask(Dose dose) {
            this.dose = dose;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                cancelAlarm(dose);
                mhealthmedViewModel.deleteDose(dose);
                return R.string.sucess;
            } catch (Exception e) {
                Log.e("ERROR", "doInBackground: " + e.getMessage());
                return R.string.error;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
                Snackbar.make(getView(), integer, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void setAlarm(Date date, float n_hours, Dose dose) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hours = (int) n_hours;
        int mins = (int) ((n_hours - hours) * 60);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy -- hh:mm");

        Log.d("ALARM", "setAlarm: " + df.format(cal.getTime()));
        cal.add(Calendar.HOUR_OF_DAY, hours);
        cal.add(Calendar.MINUTE, mins);
        Log.d("ALARM", "setAlarm: " + df.format(cal.getTime()));
        Intent intent = new Intent(getActivity() , AlarmReceiverActivity.class);
        String[] mArray = getActivity().getResources().getStringArray(R.array.medication_measurement);
        String msg = dose.getSize() +mArray[dose.getId_dose_type()]+" " +dose.getName();
        intent.putExtra("msg",msg );
        intent.putExtra("end",df.format(dose.getEnd_date()));
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                dose.getId_dose(), intent, 0);
        AlarmManager am =
                (AlarmManager) getContext().getSystemService(Activity.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                1000 * 60 * (mins + hours * 60), pendingIntent);
    }


    private void cancelAlarm(Dose dose){

        Snackbar.make(getView(), R.string.alarm_cancelled,Snackbar.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), AlarmReceiverActivity.class);
        String[] mArray = getActivity().getResources().getStringArray(R.array.medication_measurement);
        String msg = dose.getSize() +mArray[dose.getId_dose_type()]+" " +dose.getName();
        intent.putExtra("msg",msg );
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy -- hh:mm");
        intent.putExtra("end",df.format(dose.getEnd_date()));
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(),
                dose.getId_dose(), intent, 0);
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

    }

}