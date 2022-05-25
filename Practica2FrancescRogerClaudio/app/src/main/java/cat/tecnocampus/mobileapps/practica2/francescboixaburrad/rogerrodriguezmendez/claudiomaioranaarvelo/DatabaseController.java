package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DatabaseController {
    private UserDao userDao;
    private GameDao gameDao;
    private LiveData<List<UserWithGames>> allUserWithGames;

    public DatabaseController(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userDao = db.userDao();
        this.gameDao = db.gameDao();
        this.allUserWithGames = userDao.getUserWithGames();
    }


    public LiveData<List<UserWithGames>> fetchAll() {
        return allUserWithGames;
    }

    public void setUser(String nickName, int totalScore){
        User user = new User(nickName,totalScore);
        new insertAsyncUser(userDao).execute(user);

    }

    public void setGame(int gameId, int numIntents, int scoreGame, String nickOwnerGame){
        Game game = new Game(gameId,numIntents,scoreGame,nickOwnerGame);
        new insertAsyncGame(gameDao).execute(game);
    }


    private static class insertAsyncUser{
        private UserDao userDao;
        private Executor executor = Executors.newSingleThreadExecutor();

        insertAsyncUser(UserDao user){
            userDao =user;
        }

        public void execute(User user){this.doInBackground(user);}

        private void doInBackground(final User user) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    userDao.insert(user);
                }
            });
        }
    }

    private static class insertAsyncGame{
        private GameDao gameDao;
        private Executor executor = Executors.newSingleThreadExecutor();

        insertAsyncGame(GameDao game){
            gameDao = game;
        }

        public void execute(Game game){this.doInBackground(game);}

        private void doInBackground(final Game game) {
            this.executor.execute(new Runnable() {
                @Override
                public void run() {
                    gameDao.insert(game);
                }
            });
        }
    }
}
