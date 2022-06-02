package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class InfoPlayerActivity extends AppCompatActivity {

    TextView tv_wellcome;
    RecyclerView rv_llista;
    RecyclerView.LayoutManager layoutManager;
    GameAdapter gameAdapter;

    UserViewModel userViewModel;
    List<Game> allGames;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_player);

        tv_wellcome = findViewById(R.id.TV_wellCome);
        rv_llista = findViewById(R.id.RV_llistaGames);

        allGames = new LinkedList<Game>();

        nickname = getIntent().getStringExtra("nicknameRanking");
        tv_wellcome.setText(getResources().getString(R.string.TV_wellCome).replace("@NaMe@", nickname));

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsersGame().observe(this, new Observer<List<UserWithGames>>() {
            @Override
            public void onChanged(List<UserWithGames> userWithGames) {
                try{
                    List<UserWithGames> allUsers = userViewModel.getAllUsersGame().getValue();

                    for(UserWithGames users: allUsers){
                        if(users.user.nickname.equals(nickname)){
                            allGames = users.games;
                            gameAdapter.refreshData(allGames, getApplicationContext());
                            break;
                        }
                    }
                }catch (Exception e){

                }
            }
        });


        layoutManager = new LinearLayoutManager( this);
        rv_llista.setLayoutManager(layoutManager);

        gameAdapter = new GameAdapter(allGames);
        rv_llista.setAdapter(gameAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent();
        intent.putExtra("playing", "false");
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean orientationLand = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);

        if(orientationLand){
            layoutManager = new GridLayoutManager(this, 2);
        }else {
            layoutManager = new LinearLayoutManager(this);
        }

        rv_llista.setLayoutManager(layoutManager);
    }
}