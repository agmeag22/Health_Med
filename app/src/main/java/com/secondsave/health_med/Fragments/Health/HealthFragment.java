package com.secondsave.health_med.Fragments.Health;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.secondsave.health_med.Database.Entities.IMCEntry;
import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.Database.HealthMedDatabase;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;
import com.secondsave.health_med.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HealthFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HealthFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View v;

    public HealthFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_health, container, false);
        // Inflate the layout for this fragment
        ScatterChart chart = v.findViewById(R.id.chart);

//        List<Entry> entries = new ArrayList<Entry>();
//        List<IMCEntry> list = new ArrayList<>();
//        list.add(new IMCEntry(1, 1, ))
//        int i = 0;
//        for (IMCEntry data : list) {
//            entries.add(new Entry(data.getWeight(), i++));
//        }
        doInBackGround task = new doInBackGround();
        task.execute();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class doInBackGround extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HealthMedViewModel mhealthmedViewModel = ViewModelProviders.of(getActivity()).get(HealthMedViewModel.class);
            SharedPreferences prefs = getContext().getSharedPreferences(
                    "com.secondsave.health_med", getContext().MODE_PRIVATE);
            String user = prefs.getString("username","");
            User u = mhealthmedViewModel.getUserByUsernameAsync(user);
            if(u!=null) {
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
