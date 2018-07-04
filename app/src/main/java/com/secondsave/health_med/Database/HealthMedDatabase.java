package com.secondsave.health_med.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.secondsave.health_med.Converters.DateTypeConverter;
import com.secondsave.health_med.Database.Dao.DoseDao;
import com.secondsave.health_med.Database.Dao.PersonalInfoDao;
import com.secondsave.health_med.Database.Dao.RemindersDao;
import com.secondsave.health_med.Database.Dao.UserDao;
import com.secondsave.health_med.Database.Dao.ValuesDao;
import com.secondsave.health_med.Database.Entities.*;

import java.sql.Date;
import java.util.Calendar;

@Database(entities = {User.class, IMCEntry.class, Reminder.class, PersonalInfo.class, Dose.class}, version = 2)
@TypeConverters({DateTypeConverter.class})
public abstract class HealthMedDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ValuesDao valuesDao();
    public abstract PersonalInfoDao personalInfoDao();
    public abstract DoseDao doseDao();
    public abstract RemindersDao reminderDao();


    //TODO: HACER Y AGREGAR LOS DAO RESTANTES
    private static HealthMedDatabase INSTANCE;

    public static HealthMedDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HealthMedDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HealthMedDatabase.class, "health_med_database")
                            .addCallback(sRoomDatabaseCallback).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final UserDao mDao;
        private final PersonalInfoDao personalInfoDao;

        PopulateDbAsync(HealthMedDatabase db) {
            mDao = db.userDao();
            personalInfoDao = db.personalInfoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            User user = new User("electroanime2009@gmail.com","1234","" );
            mDao.insert(user);
            PersonalInfo personalInfo = new PersonalInfo("electroanime2009@gmail.com","Allen","Perez", 1, new Date(Calendar.getInstance().getTimeInMillis()));
            personalInfoDao.insert(personalInfo);
            user = new User("meag", "1234", "");
            mDao.insert(user);
            personalInfo = new PersonalInfo("meag", "Miguel", "Aviles",1, new Date(Calendar.getInstance().getTimeInMillis()));
            personalInfoDao.insert(personalInfo);
            user = new User("agwolfox@gmail.com", "1234", "");
            mDao.insert(user);
            personalInfo = new PersonalInfo("agwolfox@gmail.com", "Miguel", "Aviles",1, new Date(Calendar.getInstance().getTimeInMillis()));
            personalInfoDao.insert(personalInfo);

            return null;
        }
    }


}

