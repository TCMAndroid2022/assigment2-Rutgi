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

public class MainActivity extends AppCompatActivity {

    List<UserWithGames> allUsers;
    List<String> llistaNicknames;

    EditText et_nickname;
    UserViewModel userViewModel;
    Button b_sendNickname;


    ActivityResultLauncher<Intent> myActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allUsers = new ArrayList<UserWithGames>();
        llistaNicknames = new LinkedList<String>();

        et_nickname = findViewById(R.id.ET_inputNickname);
        b_sendNickname = findViewById(R.id.B_sendNickname);

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

                if(str.equals("")){
                    et_nickname.setError(getResources().getString(R.string.errorNickname));
                    return;
                }

                if (!llistaNicknames.contains(str)) {
                    userViewModel.insertUser(str, 0);//creem el usuari
                }

                jugar(str);
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
                        Toast.makeText(getApplicationContext(), "Ving de jugar", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Ving de ranking", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Cancelat", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void jugar(String nickname){
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("nicknameGame",nickname);
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