package com.secondsave.health_med.Database;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.secondsave.health_med.Dao.UserDao;
import com.secondsave.health_med.Entities.*;
@Database(entities = {User.class, Values.class, ValuesType.class, Reminder.class,PersonalInfo.class,Dose.class,DoseType.class }, version = 1)
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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
