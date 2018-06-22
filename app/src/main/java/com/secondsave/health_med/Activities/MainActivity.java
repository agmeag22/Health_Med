package com.secondsave.health_med.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.secondsave.health_med.Fragments.DoseFragment;
import com.secondsave.health_med.Fragments.HealthFragment;
import com.secondsave.health_med.Fragments.PharmacyFragment;
import com.secondsave.health_med.Fragments.ProfileFragment;
import com.secondsave.health_med.Fragments.RemindersFragment;
import com.secondsave.health_med.Menu.MenuModel;
import com.secondsave.health_med.R;
import com.secondsave.health_med.ViewModels.HealthMedViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView expandableListView;
    private List<MenuModel> headerList = new ArrayList<>();
    private HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    private String reminders_menu_title, statitics_menu_title, pharmacies_menu_title, health_menu_title, profile_menu_title, settings_menu_title, rateus_menu_title, logout_menu_title;
    private List<MenuModel> childModelsList;
    private LiveData<List<String>> listLiveData;
    private HealthMedViewModel mhealthmedViewModel;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getSharedPreferences(
                "com.secondsave.health_med", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        TextView userName, email;
        View navigationheader = navigationView.getHeaderView(0);
        userName = navigationheader.findViewById(R.id.name_txt);
        email = navigationheader.findViewById(R.id.email_txt);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();
        //PRUEBA DE BD
//        RecyclerView recyclerView = findViewById(R.id.recycler);
//        final UserAdapter adapter = new UserAdapter();
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mhealthmedViewModel = ViewModelProviders.of(this).get(HealthMedViewModel.class);
//        mhealthmedViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
//            @Override
//            public void onChanged(@Nullable final List<User> users) {
//                // Update the cached copy of the words in the adapter.
//                adapter.setmUsers(users);
//            }
//        });

        String token = prefs.getString("token","");
        String user = prefs.getString("username","");
        String name = prefs.getString("name","");
        userName.setText(name);
        email.setText(user);
        if(name.equals("") ||user.equals("") || token.equals("") || !mhealthmedViewModel.isUserAndTokenMatch(user,token)) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
    }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        if (id == R.id.navigation_logout) {

        }
        if (id == R.id.navigation_reminders) {

        } else if (id == R.id.navigation_dose){
            fragment=new DoseFragment();
            transaction.replace(R.id.container, fragment).commit();
        } else if (id == R.id.navigation_pharmacy) {

        } else if (id == R.id.navigation_health) {

        } else if (id == R.id.navigation_profile) {

        }
//          else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void prepareMenuData() {

        get_menu_titles();
        MenuModel menuModel = new MenuModel(profile_menu_title, false, true); //Menu of Java Tutorials
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
                        if (headerList.get(groupPosition).menuName.equals(profile_menu_title)) {
                            fragment = new ProfileFragment();
                            transaction.replace(R.id.container, fragment).commit();

                        }
                        if (headerList.get(groupPosition).menuName.equals(reminders_menu_title)) {
                            fragment = new RemindersFragment();
                            transaction.replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(statitics_menu_title)) {
                        }
                        if (headerList.get(groupPosition).menuName.equals(pharmacies_menu_title)) {
                            fragment = new PharmacyFragment();
                            transaction.replace(R.id.container, fragment).commit();
                        }
                        if (headerList.get(groupPosition).menuName.equals(health_menu_title)) {
                            fragment = new HealthFragment();
                            transaction.replace(R.id.container, fragment).commit();
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

    public void get_menu_titles() {
        profile_menu_title = getString(R.string.profile_menu_title);
        reminders_menu_title = getString(R.string.reminder_menu_title);
        statitics_menu_title = getString(R.string.statistics_menu_title);
        pharmacies_menu_title = getString(R.string.pharmacies_menu_title);
        health_menu_title = getString(R.string.health_menu_title);
        settings_menu_title = getString(R.string.settings_menu_title);
        rateus_menu_title = getString(R.string.rateus_menu_title);
        logout_menu_title = getString(R.string.logout_menu_title);
    }

}
