package hu.unideb.inf.cardata.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import hu.unideb.inf.cardata.model.Car;

@Database(entities = {Car.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarDao carDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "car_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
