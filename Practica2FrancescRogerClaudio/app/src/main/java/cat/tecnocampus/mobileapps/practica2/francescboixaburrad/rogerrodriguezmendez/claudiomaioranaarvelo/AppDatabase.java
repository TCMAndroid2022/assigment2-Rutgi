package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Game.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract  UserDao userDao();
    public abstract  GameDao gameDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Paraules").build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){ INSTANCE = null; }
}
