package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();


    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<UserWithGames>> getUserWithGames();
}
