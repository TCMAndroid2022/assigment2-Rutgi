package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    TextView tv_output;
    Button b_getRand;
    Button b_addLetter;
    RequestQueue rqueue;
    EditText etx_placeToWrite;
    String urlRandom = "https://palabras-aleatorias-public-api.herokuapp.com/random";

    String wordResult="";
    String wordPlayer="";

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_output = findViewById(R.id.TV_output);
        b_getRand = findViewById(R.id.B_getword);
        b_addLetter = findViewById(R.id.B_addLetter);
        etx_placeToWrite = findViewById(R.id.etx_placeToWrite);
        rqueue = Volley.newRequestQueue(getApplicationContext());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getAllUsersGame().observe(this, new Observer<List<UserWithGames>>() {
            @Override
            public void onChanged(List<UserWithGames> userWithGames) {
                try{
                    List<UserWithGames> allUsers = userViewModel.getAllUsersGame().getValue();
                    Log.v("Clau",allUsers.toString());
                }catch (Exception e){

                }
            }
        });

        userViewModel.insertUser("rutgi",5);
        userViewModel.insertGame(1,1,100,"maio");

        b_getRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWord();

            }
        });

        b_addLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etx_placeToWrite.getText().toString().equals("") ){
                    if( !wordResult.contains(etx_placeToWrite.getText().toString())){
                        Toast.makeText(getApplicationContext(),"No hi ha lletra",Toast.LENGTH_LONG).show();
                    }else{
                        addLetter(etx_placeToWrite.getText().toString().charAt(0));
                    }
                }

            }
        });

    }

    private void getWord(){
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRandom, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String strResponse = response.getJSONObject("body").getString("Word");
                            wordResult = strResponse;
                            //tv_output.setText(strResponse);
                            wordPlayer="";
                            setWord();
                            Log.d("Numero Letras", wordResult);

                        } catch (Exception ex) {
                            tv_output.setText("Json Request failed");
                            Log.d("SwA", "Error, parsing json array?");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tv_output.setText("Json Request failed");
                        Log.d("SwA", "Error in Json Request ");
                    }
                }
        );
        rqueue.add(jsonRequest);
    }



    private int numberLetters(String word){
        return word.length();
    }

    private void setWord(){
        for(int i = 0;i<numberLetters(wordResult);i++){
            wordPlayer+="_";
        }
        tv_output.setText(wordPlayer);
    }

    private void addLetter(char letter){

        StringBuilder myString = new StringBuilder(wordPlayer);
        for(int i = 0;i<numberLetters(wordResult);i++){
            if(wordResult.charAt(i) == letter){
                myString.setCharAt(i,letter);

            }
        }
        wordPlayer = myString.toString();
        tv_output.setText(wordPlayer);

    }
}