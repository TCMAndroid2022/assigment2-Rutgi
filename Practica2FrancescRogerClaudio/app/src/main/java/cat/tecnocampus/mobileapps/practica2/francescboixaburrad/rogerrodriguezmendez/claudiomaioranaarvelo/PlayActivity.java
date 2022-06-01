package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

public class PlayActivity extends AppCompatActivity {


    TextView tv_output, tv_nickname, tv_wellComeTitle;
    Button b_getRand,  b_addLetter;
    EditText et_placeToWrite;

    //String urlRandom = "https://random-word-api.herokuapp.com/word";
    String urlRandom = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    String wordResult="", wordPlayer="";

    RequestQueue rqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        tv_output = findViewById(R.id.TV_outputWord);
        tv_nickname = findViewById(R.id.TV_nickname);
        tv_nickname = findViewById(R.id.TV_titolPlay);
        b_getRand = findViewById(R.id.B_getword);
        b_addLetter = findViewById(R.id.B_addLetter);
        et_placeToWrite = findViewById(R.id.ET_writeLetter);

        String strExtra = getIntent().getStringExtra("nicknameGame");
        tv_nickname.setText(getResources().getString(R.string.TV_TitlePlay).replace("@NaMe@", strExtra));

        rqueue = Volley.newRequestQueue(getApplicationContext());

        b_getRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWord();
            }
        });

        b_addLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_placeToWrite.getText().toString().equals("") ){
                    if( !wordResult.contains(et_placeToWrite.getText().toString())){
                        Toast.makeText(getApplicationContext(),"No hi ha lletra",Toast.LENGTH_LONG).show();
                    }else{
                        addLetter(et_placeToWrite.getText().toString().charAt(0));
                    }
                }

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBarAddTitle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void getWord(){
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlRandom, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String strResponse = response.getJSONObject("body").getString("Word");
                            wordResult = strResponse;
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