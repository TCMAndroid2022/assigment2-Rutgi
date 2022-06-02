package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private DatabaseController repository;
    private LiveData<List<UserWithGames>> allUsersGame;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new DatabaseController(application);
        allUsersGame = repository.fetchAll();
    }

    LiveData<List<UserWithGames>> getAllUsersGame(){
        return allUsersGame;
    }

    public void insertUser(String nickName, int totalScore){
        repository.setUser(nickName,totalScore);
    }

    public void deleteUser(String nickName){
        repository.deleteUser(nickName);
    }

    public void updateUser(String nickName, int totalScore){
        repository.updateUser(nickName, totalScore);
    }

    public void insertGame(int gameId, int numIntents, int scoreGame, String nickOwnerGame){
        repository.setGame(gameId, numIntents, scoreGame, nickOwnerGame);
    }

}
