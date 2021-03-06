package com.secondsave.health_med.Fragments.Health;

import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.secondsave.health_med.Adapters.IMCAdapter;
import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;

import java.util.List;

/**
 * This Fragment shows an advance of the user's health in terms of IMC
 * */
public class HealthFragment extends Fragment {
    /**
     * Simple view
     * */
    private View v;
    /**
     * ViewModel with app repository methods
     * */
    private HealthMedViewModel mhealthmedViewModel;
    /**
     * SharePreferences to save data on the app's database
     * */
    private SharedPreferences prefs;
    /**
     * user's name
     * */
    private String user;
    /**
     * RecyclerView which will contain IMC list items.
     * */
    private RecyclerView recycler;
    /**
     * Adapter which inflates item_imc_list.xml
     * */
    private IMCAdapter adapter;
    /**
     * Textview for showing data
     * */
    private TextView c_weight, i_weight, goal_weight;
    /**
     * Textview for showing actual progress.
     * */
    private TextView txtProgress;
    /**
     * Button to add an IMC entry
     * */
    FloatingActionButton fb;
    /**
     * shows up when loading
     * */
    private ProgressBar progressBar;
    /**
     * weight value
     * */
    private float goal, initial, current;
    /**
     * weight goal
     * */
    private View goalView;
    /**
     * last weight
     * */

    private float lastweight;

    /**
     * Empty Constructor
     * */
    public HealthFragment() {
        // Required empty public constructor
    }

    /**
     * This method creates and infates the view, set views content and calculates IMC. Having an observer for the actual changes on the database for adding information and
     * updating the adapters.
     * */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_health, container, false);
        //Set up variables
        mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
        prefs = getContext().getSharedPreferences(
                "com.secondsave.health_med", getContext().MODE_PRIVATE);
        user = prefs.getString("username", "");
        recycler = v.findViewById(R.id.recycler);
        i_weight = v.findViewById(R.id.initial_weight);
        goal_weight = v.findViewById(R.id.goal);
        c_weight = v.findViewById(R.id.cur_weight);
        txtProgress = v.findViewById(R.id.txtProgress);
        progressBar = v.findViewById(R.id.progressBar);
        goal = prefs.getFloat("goal", 0);
        initial = prefs.getFloat("initial", 0);
        fb = v.findViewById(R.id.add_entry);
        goalView = v.findViewById(R.id.goal_progress);
        goalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(goal==0 || initial==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Set your goal Weight");
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(input);
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String text = input.getText().toString();
                        if (!text.equals(""))
                            prefs.edit().putFloat("goal", Float.parseFloat(text)).commit();
                        prefs.edit().putFloat("initial", current).commit();
                        initial = current;
                        i_weight.setText(initial + " kg");
                        goal = Float.parseFloat(text);
                        int value = Math.round(Math.abs(initial - current) / Math.abs(goal - initial)) * 100;
                        progressBar.setProgress(value);
                        txtProgress.setText(value + " %");
                        goal_weight.setText(goal + "");
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder.show();
//                }
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewEntryDialog dialog = new NewEntryDialog();
                dialog.show(getChildFragmentManager(), "addIMC");
            }
        });
        adapter = new IMCAdapter(null);
        recycler.setAdapter(adapter);
//        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        LiveData<List<IMCEntry>> list = mhealthmedViewModel.getAllValuesByUsername(user);
        list.observe(this, new Observer<List<IMCEntry>>() {
            @Override
            public void onChanged(@Nullable List<IMCEntry> imcEntries) {

                if (imcEntries.size() > 0) {
                    if (imcEntries.size() > 1) {
                        c_weight.setText(imcEntries.get(imcEntries.size() - 1).getWeight() + " kg");
                        current = imcEntries.get(imcEntries.size() - 1).getWeight();
                    } else {
                        c_weight.setText(imcEntries.get(imcEntries.size() - 1).getWeight() + " kg");
                        current = imcEntries.get(imcEntries.size() - 1).getWeight();
                    }
                    i_weight.setText("-");

                }
                if (goal != 0 && initial != 0) {
                    lastweight = imcEntries.get(imcEntries.size() - 1).getWeight();
                    int value = Math.round(Math.abs(((initial - current)) / Math.abs((goal - initial))) * 100);
                    progressBar.setProgress(value);
                    txtProgress.setText(value + " %");
                    goal_weight.setText(goal + "");
                    i_weight.setText(initial + " kg");
                }
                adapter.setList(imcEntries);
                adapter.notifyDataSetChanged();
            }
        });

        doInBackGround task = new doInBackGround();
        task.execute();
        return v;
    }

    /**
     * This class specifies database handling background actions. Starts SetUpFragment.
     * */

    public class doInBackGround extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            User u = mhealthmedViewModel.getUserByUsernameAsync(user);
            if (u != null) {
                int IMC_entries = mhealthmedViewModel.countValuesByUsername(user);
                if (IMC_entries == 0) {
                    Fragment fragment = new SetUpFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(android.R.id.content, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void integer) {

        }
    }
}
