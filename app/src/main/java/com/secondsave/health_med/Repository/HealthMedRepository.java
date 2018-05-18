package com.secondsave.health_med.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.secondsave.health_med.Dao.UserDao;
import com.secondsave.health_med.Database.HealthMedDatabase;
import com.secondsave.health_med.Entities.User;

import java.util.List;

public class HealthMedRepository {
    private UserDao mUserDao;
    private LiveData<List<User>> mUsers;

    public HealthMedRepository(Application application) {
        HealthMedDatabase db = HealthMedDatabase.getDatabase(application);
        mUserDao = db.userDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return mUsers;
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
