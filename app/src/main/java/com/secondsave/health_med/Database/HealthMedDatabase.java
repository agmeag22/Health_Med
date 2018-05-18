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
import com.secondsave.health_med.Dao.UserDao;
import com.secondsave.health_med.Entities.*;

@Database(entities = {User.class, Values.class, ValuesType.class, Reminder.class, PersonalInfo.class, Dose.class, DoseType.class}, version = 1)
@TypeConverters({DateTypeConverter.class})
public abstract class HealthMedDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    //TODO: HACER Y AGREGAR LOS DAO RESTANTES
    private static HealthMedDatabase INSTANCE;

    public static HealthMedDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (HealthMedDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HealthMedDatabase.class, "health_med_database")
                            .addCallback(sRoomDatabaseCallback).build();
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

        PopulateDbAsync(HealthMedDatabase db) {
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            User user = new User("Allen","1234");
            mDao.insert(user);
            user = new User("Miguel","1234");
            mDao.insert(user);
            return null;
        }
    }


}

