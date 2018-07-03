package com.secondsave.health_med.Database.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;


import com.secondsave.health_med.Database.Entities.PersonalInfo;
import com.secondsave.health_med.Database.Entities.User;
import com.secondsave.health_med.Database.Entities.Values;
import com.secondsave.health_med.Database.Repository.HealthMedRepository;

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
    public User getUserByUsernameAsync(String username){
        return mRepository.getUserAsync(username);
    }

    public PersonalInfo getPersonalInfo(User user){
        return mRepository.getUserPersonalInfo(user.getId_user());
    }
    public PersonalInfo getPersonalInfoAsync(User user){
        return mRepository.getUserPersonalInfoAsync(user.getId_user());
    }

    public LiveData<List<Values>> getAllValuesByUserIdAndType(int user_id, int type) {
        return mRepository.getAllValuesByUserIdAndType(user_id,type);
    }
    public int countValuesByUserIdAndType(int user_id, int type) {
        return mRepository.countValuesByUserIdAndType(user_id,type);
    }

    public void insertPersonaInfo(PersonalInfo personalInfo){
        mRepository.insertPersonaInfo(personalInfo);
    }

    public void insertValues(Values values){
        mRepository.insertValues(values);
    }
}

