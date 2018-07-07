package com.secondsave.health_med.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.secondsave.health_med.R;

import java.nio.FloatBuffer;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    private boolean isOpen = false;
    private ConstraintSet layout1, layout2;
    private ConstraintLayout constraintLayout;
    private de.hdodenhof.circleimageview.CircleImageView imageView;
    private CardView cardView;
    private FloatingActionButton edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Window w = getActivity().getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        edit = v.findViewById(R.id.floating_edit);
        edit.setVisibility(View.GONE);
        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        imageView = v.findViewById(R.id.profile_image);
        constraintLayout = v.findViewById(R.id.profilefragment);
        layout2.clone(getContext(), R.layout.fragment_profile_expanded);
        layout1.clone(constraintLayout);

        cardView = v.findViewById(R.id.cardView2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    edit.setVisibility(View.VISIBLE);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragment = new ProfileEdit();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null).replace(R.id.profilefragment, fragment).commit();
                        }
                    });
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(constraintLayout);
                    }
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen;

                } else {
                    edit.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(constraintLayout);
                    }
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen;
                }
            }
        });
        return v;
    }

}
