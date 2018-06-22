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
import com.secondsave.health_med.Dao.PersonalInfoDao;
import com.secondsave.health_med.Dao.UserDao;
import com.secondsave.health_med.Dao.ValuesDao;
import com.secondsave.health_med.Entities.*;

@Database(entities = {User.class, Values.class, ValuesType.class, Reminder.class, PersonalInfo.class, Dose.class, DoseType.class}, version = 2)
@TypeConverters({DateTypeConverter.class})
public abstract class HealthMedDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ValuesDao valuesDao();
    public abstract PersonalInfoDao personalInfoDao();

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
            long id= mDao.insert(user);
            PersonalInfo personalInfo = new PersonalInfo((int)id,"Allen","Perez");
            personalInfoDao.insert(personalInfo);
            user = new User("agmeag@gmail.com","1234", "");
            id = mDao.insert(user);
            new PersonalInfo((int)id,"Miguel","Aviles");
            personalInfoDao.insert(personalInfo);
            return null;
        }
    }


}

