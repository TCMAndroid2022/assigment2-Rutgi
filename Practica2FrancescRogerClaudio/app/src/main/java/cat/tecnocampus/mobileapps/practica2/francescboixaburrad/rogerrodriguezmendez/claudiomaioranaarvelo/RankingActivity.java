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
import java.util.List;

public class RankingActivity extends AppCompatActivity implements TodoAdapter.OnRVtodoListener{

    RecyclerView rv_llista;
    TodoAdapter todoAdapter;
    RecyclerView.LayoutManager layoutManager;

    ActivityResultLauncher<Intent> myActivityResultLauncherInfo;
    UserViewModel userViewModel;
    private int indexModify;
    String tipusIndex;
    List<UserWithGames> allUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankinga);
        rv_llista = findViewById(R.id.RV_lista);

        allUsers = new ArrayList<UserWithGames>();
        tipusIndex = getIntent().getStringExtra("dataSent");
        layoutManager = new LinearLayoutManager( this);
        rv_llista.setLayoutManager(layoutManager);

        todoAdapter = new TodoAdapter(allUsers, this);
        rv_llista.setAdapter(todoAdapter);


        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        todoAdapter = new TodoAdapter(allUsers, this);
        rv_llista.setAdapter(todoAdapter);
        userViewModel.getAllUsersGame().observe(this, new Observer<List<UserWithGames>>() {
            @Override
            public void onChanged(List<UserWithGames> userWithGames) {
                try{
                    allUsers = userViewModel.getAllUsersGame().getValue();
                    todoAdapter.refreshData(allUsers);
                    Log.v("Clau",allUsers.toString());
                }catch (Exception e){

                }
            }
        });


       /* if(savedInstanceState==null){
            exemple();

        }*/

        myActivityResultLauncherInfo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

            }
        });
    }


   private void exemple(){
        userViewModel.insertUser("Maio",5);
        userViewModel.insertGame(1,1,100,"rutgi");
        todoAdapter.notifyDataSetChanged();

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
        intent.putExtra("nomEnviat", currentTodo.user.getNickName());
        intent.putExtra("tipoEnviat", currentTodo.user.getTotalScore());
        intent.putExtra("telefEnviat", currentTodo.user.getNumMach());
        try{
            indexModify = posicio;
            myActivityResultLauncherInfo.launch(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),  R.string.errorDoing, Toast.LENGTH_LONG).show();
            indexModify = -1;
        }
    }
}