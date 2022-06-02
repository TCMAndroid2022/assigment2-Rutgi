package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<UserWithGames> allUsers;
    List<String> llistaNicknames;

    EditText et_nickname;
    UserViewModel userViewModel;
    Button b_sendNickname;
    TextView tv_lastPlay;


    ActivityResultLauncher<Intent> myActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allUsers = new ArrayList<UserWithGames>();
        llistaNicknames = new LinkedList<String>();

        et_nickname = findViewById(R.id.ET_inputNickname);
        b_sendNickname = findViewById(R.id.B_sendNickname);
        tv_lastPlay = findViewById(R.id.TV_lastPlay);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUsersGame().observe(this, new Observer<List<UserWithGames>>() {
            @Override
            public void onChanged(List<UserWithGames> userWithGames) {
                try{
                    allUsers = userViewModel.getAllUsersGame().getValue();
                    for (UserWithGames user: allUsers) {
                        String stringNickname  = user.user.nickname;
                        llistaNicknames.add(stringNickname);
                    }
                }catch (Exception e){

                }
            }
        });

        //exemple();

        b_sendNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = et_nickname.getText().toString();
                str.replaceAll(" ","");//no volem espais en blanc
                int oldScore = 0;

                if(str.equals("")){
                    et_nickname.setError(getResources().getString(R.string.errorNickname));
                    return;
                }

                if (!llistaNicknames.contains(str)) {
                    userViewModel.insertUser(str, 0);//creem el usuari
                }
                else
                {
                    for (UserWithGames users: allUsers) {
                        if(users.user.nickname.equals(str)){
                            oldScore = users.user.totalscore;
                            break;
                        }
                    }
                }

                jugar(str, oldScore);
                et_nickname.getText().clear();
            }
        });

        myActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Intent intent = result.getData();
                Boolean playing = Boolean.valueOf(intent.getStringExtra("playing"));


                if(result.getResultCode() == RESULT_OK){
                    if(playing){
                        String punts = intent.getStringExtra("punts");
                        String nickname = intent.getStringExtra("nickname");
                        String intents = intent.getStringExtra("intents");
                        String oldScore = intent.getStringExtra("oldScore");
                        Toast.makeText(getApplicationContext(), "Punts: "+punts+", OldScore: "+oldScore, Toast.LENGTH_LONG).show();

                        Random rand = new Random();
                        int ID = rand.nextInt();

                        userViewModel.insertGame(ID, Integer.valueOf(intents), Integer.valueOf(punts), nickname);
                        userViewModel.updateUser(nickname, Integer.valueOf(oldScore) + Integer.valueOf(punts));
                        tv_lastPlay.setText(getResources().getString(R.string.TV_lastPlay).replace("@NaMe@",nickname).replace("@pUnTs@", punts));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Ving de ranking", Toast.LENGTH_LONG).show();
                        tv_lastPlay.setText("");
                    }
                }
                else
                {
                    tv_lastPlay.setText(getResources().getString(R.string.Cancel));
                }
            }
        });
    }

    private void jugar(String nickname, int oldScore){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("nicknameGame",nickname);
        intent.putExtra("oldScore",String.valueOf(oldScore));
        try{
            myActivityResultLauncher.launch(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),  R.string.errorDoing, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.m_ranking_go:

                intent = new Intent(this, RankingActivity.class);
                try{
                    myActivityResultLauncher.launch(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(),  R.string.errorDoing, Toast.LENGTH_LONG).show();
                }
                break;
        }
        return (super.onOptionsItemSelected(item));
    }



    private void exemple(){
        userViewModel.insertUser("Maio",5);
        userViewModel.insertGame(1,1,100,"Maio");
    }
}