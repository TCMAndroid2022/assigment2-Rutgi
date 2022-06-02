package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity implements TodoAdapter.OnRVtodoListener{

    RecyclerView rv_llista;
    TodoAdapter todoAdapter;
    RecyclerView.LayoutManager layoutManager;

    ActivityResultLauncher<Intent> myActivityResultLauncherInfo;
    UserViewModel userViewModel;
    List<UserWithGames> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankinga);
        rv_llista = findViewById(R.id.RV_lista);

        allUsers = new ArrayList<UserWithGames>();
        layoutManager = new LinearLayoutManager( this);
        rv_llista.setLayoutManager(layoutManager);

        todoAdapter = new TodoAdapter(allUsers, this);
        rv_llista.setAdapter(todoAdapter);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsersGame().observe(this, new Observer<List<UserWithGames>>() {
            @Override
            public void onChanged(List<UserWithGames> userWithGames) {
                try{
                    allUsers = userViewModel.getAllUsersGame().getValue();
                    Collections.sort(allUsers, new Comparator<UserWithGames>() {
                        @Override
                        public int compare(UserWithGames o1, UserWithGames o2) {
                            return o2.user.totalscore - o1.user.totalscore;
                        }
                    });
                    todoAdapter.refreshData(allUsers);
                    Log.v("Clau",allUsers.toString());
                }catch (Exception e){

                }
            }
        });

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


    @Override
    public void onRVtodoListener(int posicio) {
        UserWithGames currentTodo = allUsers.get(posicio);
        Intent intent = new Intent(this, InfoPlayerActivity.class);
        intent.putExtra("nicknameRanking", currentTodo.user.getNickName());
        try{
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),  R.string.errorDoing, Toast.LENGTH_LONG).show();
        }
    }
}