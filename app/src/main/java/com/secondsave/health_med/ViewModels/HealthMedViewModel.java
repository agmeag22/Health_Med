package com.secondsave.health_med.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.secondsave.health_med.Entities.PersonalInfo;
import com.secondsave.health_med.Entities.User;
import com.secondsave.health_med.Repository.HealthMedRepository;

import java.util.List;

public class HealthMedViewModel extends AndroidViewModel {
    private HealthMedRepository mRepository;

    private LiveData<List<User>> mAllUsers;

    public HealthMedViewModel(Application application) {
        super(application);
        mRepository = new HealthMedRepository(application);
        mAllUsers = mRepository.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void insert(User user) {
        mRepository.insert(user);
    }

    public boolean isUserAndPasswordMatch(String user, String password){
        return mRepository.isUserAndPasswordMatch(user,password);
    }

    public void updateUserToken(String user, String token){
        mRepository.updateToken(user,token);
    }

    public boolean isUserAndTokenMatch(String user, String token){
        return mRepository.isUserAndTokenMatch(user,token);
    }

    public User getUserByUsername(String username){
        return mRepository.getUser(username);
    }

    public PersonalInfo getPersonalInfo(User user){
        return mRepository.getUserPersonalInfo(user.getId_user());
    }
}

