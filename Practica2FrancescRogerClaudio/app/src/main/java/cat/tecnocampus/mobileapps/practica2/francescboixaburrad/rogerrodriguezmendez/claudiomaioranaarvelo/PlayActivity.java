package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


    TextView tv_output, tv_wellComeTitle;
    Button b_getRand,  b_addLetter, b_addWord;
    EditText et_letterToWrite, et_wordToWrite;

    //String urlRandom = "https://random-word-api.herokuapp.com/word";
    String urlRandom = "https://palabras-aleatorias-public-api.herokuapp.com/random";
    String wordResult="", wordPlayer="";
    int counterInputs;

    RequestQueue rqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tv_output = findViewById(R.id.TV_outputWord);
        tv_wellComeTitle = findViewById(R.id.TV_titolPlay);
        b_getRand = findViewById(R.id.B_getword);
        b_addLetter = findViewById(R.id.B_addLetter);
        b_addWord = findViewById(R.id.B_addWord);
        et_letterToWrite = findViewById(R.id.ET_writeLetter);
        et_wordToWrite = findViewById(R.id.ET_writeWord);

        counterInputs = 0;

        String strExtra = getIntent().getStringExtra("nicknameGame");
        tv_wellComeTitle.setText(getResources().getString(R.string.TV_TitlePlay).replace("@NaMe@", strExtra));

        rqueue = Volley.newRequestQueue(getApplicationContext());
        getWord();

        b_getRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWord();
            }
        });

        b_addLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_letterToWrite.getText().toString().equals("") ){
                    counterInputs++;
                    if( !wordResult.contains(et_letterToWrite.getText().toString())){
                        Toast.makeText(getApplicationContext(),"No hi ha lletra",Toast.LENGTH_LONG).show();
                    }else{
                        addLetter(et_letterToWrite.getText().toString().charAt(0));
                    }
                    et_letterToWrite.getText().clear();
                }
            }
        });

        b_addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWroted = et_wordToWrite.getText().toString();
                if(!strWroted.equals("") ){
                    if( !wordResult.equals(strWroted)){
                        et_wordToWrite.setError(getResources().getString(R.string.errorWord));
                        counterInputs++;
                    }else{
                        //addLetter(et_letterToWrite.getText().toString().charAt(0));
                        tv_output.setText("CORRECTE");

                        Intent intent = new Intent();
                        intent.putExtra("playing","true");
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    et_wordToWrite.getText().clear();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBarAddTitle);
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent intent = new Intent();
        intent.putExtra("playing", "false");
        setResult(RESULT_CANCELED, intent);
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
                            Log.d("Paraula", wordResult);

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
        tv_output.setText(numberLetters(wordResult)+": "+wordPlayer);
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