package com.secondsave.health_med.Database.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.secondsave.health_med.Database.Dao.PersonalInfoDao;
import com.secondsave.health_med.Database.Dao.UserDao;
import com.secondsave.health_med.Database.HealthMedDatabase;
import com.secondsave.health_med.Database.Entities.PersonalInfo;
import com.secondsave.health_med.Database.Entities.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HealthMedRepository {
    private UserDao mUserDao;
    private PersonalInfoDao personalInfoDao;
    private LiveData<List<User>> mUsers;

    public HealthMedRepository(Application application) {
        HealthMedDatabase db = HealthMedDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mUsers = mUserDao.getAllUsers();
        personalInfoDao = db.personalInfoDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return mUsers;
    }

    public User getUser(String username){
        AsyncTask<String,Void,User> task = new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... strings) {
                return mUserDao.getUserByUsername(strings[0]);
            }
        };

        try {
            User result = task.execute(username).get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    public PersonalInfo getUserPersonalInfo(int user_id){
        AsyncTask<Integer,Void,PersonalInfo> task = new AsyncTask<Integer,Void,PersonalInfo>() {

            @Override
            protected PersonalInfo doInBackground(Integer... ints) {
                return personalInfoDao.getPersonalInfoByUserId(ints[0]);
            }
        };
        try {
            PersonalInfo result  = task.execute(user_id).get();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean isUserAndPasswordMatch(String user, String password){
        int result = mUserDao.isUserAndPasswordMatch(user,password);
        return result>0;
    }

    public boolean isUserAndTokenMatch(final String user, final String token){

        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>()
         {
            @Override
            protected Boolean doInBackground(Void... voids) {
                int result = mUserDao.isUserAndTokenMatch(user,token);
                return result>0;
            }
        };

        boolean b=false;
        try {
             b = task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return b;
    }

    public void updateToken(String user, String token){
        new updateTokenAsync(mUserDao, user, token).execute();
    }

    public static class updateTokenAsync extends AsyncTask<Void,Void,Boolean>{
        private UserDao mUserDao;
        private String user,token;

        public updateTokenAsync(UserDao mUserDao, String user, String token) {
            this.mUserDao=mUserDao;
            this.user = user;
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... Void) {
            mUserDao.updateUserToken(user,token);
            return true;
        }
    }


    public void insert(User user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
