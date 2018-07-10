package com.secondsave.health_med.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.secondsave.health_med.Fragments.Health.HealthFragment;
import com.secondsave.health_med.Fragments.HomeMenu;
import com.secondsave.health_med.Fragments.Pharmacy.PharmacyFragment;
import com.secondsave.health_med.Fragments.ProfileFragment;

import com.secondsave.health_med.Fragments.Reminders.RemindersFragment;
import com.secondsave.health_med.Menu.MenuModel;
import com.secondsave.health_med.R;
import com.secondsave.health_med.Database.ViewModels.HealthMedViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An activity  where Health,Pharmacy,Reminders, Home, and Profile fragments are executed.
 *
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
/**
 * This adapter makes menu options scalable and allow them to have suboptions.
 * */
    private ExpandableListAdapter expandableListAdapter;
    /**
     * ExpandableListView is the list used inside ExpandableListAdapter
     * */
    private ExpandableListView expandableListView;
    /**
     * Contains main menu options
     * */
    private List<MenuModel> headerList = new ArrayList<>();
    /**
     * Used for secondary menu options (child)
     * */
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    /**
     * String for menu title
     * */
    private String reminders_menu_title, statitics_menu_title, pharmacies_menu_title,
            health_menu_title, profile_menu_title, settings_menu_title,
            rateus_menu_title, logout_menu_title, home_menu_title;

    /**
     *ViewModel where all methods of the repository exists
     * */
    private HealthMedViewModel mhealthmedViewModel;
    /**
     * Will contain app saved data even when stopped, no private user information
     * */
    SharedPreferences prefs;
    int access=0;
    private Toolbar toolbar;

    /**
     *This method starts the application while showing activity_main.xml and creating a drawer menu.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences(
                "com.secondsave.health_med", MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        mhealthmedViewModel = ViewModelProviders.of(this).get(HealthMedViewModel.class);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView userName, email;
        View navigationheader = navigationView.getHeaderView(0);
        userName = navigationheader.findViewById(R.id.name_txt);
        email = navigationheader.findViewById(R.id.email_txt);
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();
        prefs.edit().putString("username", "HealthMED").apply();
        //prefs.edit().putString("name", "HealthMED").apply();
        String token = prefs.getString("token","");
        String user = prefs.getString("username","");
        String name = prefs.getString("name","");
        userName.setText(name);
        email.setText(user);
//        if (name.equals("") || user.equals("") || token.equals("") || !mhealthmedViewModel.isUserAndTokenMatch(user, token)) {
//            Intent i = new Intent(this, LoginActivity.class);
//            startActivity(i);
//            finish();
//        }

                 Fragment fragment = new HomeMenu();
                 getSupportActionBar().setTitle("HOME");
                 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                 transaction.addToBackStack(null).replace(R.id.container, fragment).commit();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     *This method closes menudrawer, or asks to the user if he or she wants to close the app, also destroys the fragment when the backstack is not empty
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backStackEntryCount == 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.question);
                builder.setMessage(R.string.exit_question);
                builder.setNegativeButton(android.R.string.no, null);

                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        getSupportFragmentManager().popBackStack();
                        MainActivity.super.onBackPressed();
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

            } else {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    /**
     *This method fills the menu on the DrawerLayout with the option's name and specifies if an option has a child or not.
     */
    private void prepareMenuData() {

        get_menu_titles();
        MenuModel menuModel = new MenuModel(home_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(profile_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(reminders_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(pharmacies_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(statitics_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(health_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(settings_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(rateus_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
        menuModel = new MenuModel(logout_menu_title, false, true); //Menu of Java Tutorials
        headerList.add(menuModel);
        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }
    }
    /**
     *This method sets the adapter to function with an expandable list and makes the visuals appear with the specified names and child of prepareMenuData()
     * Also specifies the click action when option touched.
     */
    //Setting adapter for the expandable list and making the visuals appear, + adding what would happen when selected
    private void populateExpandableList() {

        expandableListAdapter = new com.secondsave.health_med.Menu.ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        //Click listener for parent option
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                get_menu_titles();
                Fragment fragment;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        if (headerList.get(groupPosition).menuName.equals(home_menu_title)) {
                            fragment = new HomeMenu();
                            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(profile_menu_title)) {
                            fragment = new ProfileFragment();
                            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(reminders_menu_title)) {
                            fragment = new RemindersFragment();
                            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(statitics_menu_title)) {
                        }
                        if (headerList.get(groupPosition).menuName.equals(pharmacies_menu_title)) {
                            fragment = new PharmacyFragment();
                            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(health_menu_title)) {
                            fragment = new HealthFragment();
                            transaction.addToBackStack(null).replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(settings_menu_title)) {
                        }
                        if (headerList.get(groupPosition).menuName.equals(rateus_menu_title)) {
                        }
                        if (headerList.get(groupPosition).menuName.equals(logout_menu_title)) {
                            prefs = getSharedPreferences("com.secondsave.health_med", MODE_PRIVATE);
                            prefs.edit().remove("token").apply();
                            prefs.edit().remove("username").apply();
                            prefs.edit().remove("name").apply();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                        }
                        toolbar.setTitle(headerList.get(groupPosition).menuName);
                        onBackPressed();
                    }
                }
                return false;
            }
        });
        //Click listener for any child option
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    /**
     *This method assigns an string located on strings.xml to the variables shown.
     */
    public void get_menu_titles() {
        profile_menu_title = getString(R.string.profile_menu_title);
        reminders_menu_title = getString(R.string.reminder_menu_title);
        statitics_menu_title = getString(R.string.statistics_menu_title);
        pharmacies_menu_title = getString(R.string.pharmacies_menu_title);
        health_menu_title = getString(R.string.health_menu_title);
        settings_menu_title = getString(R.string.settings_menu_title);
        rateus_menu_title = getString(R.string.rateus_menu_title);
        logout_menu_title = getString(R.string.logout_menu_title);
        home_menu_title = getString(R.string.home_menu_title);
    }

    /**
     *This method closes the drawer menu where an option is selected.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
