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
    int counterInputs, oldScore;
    String nickname;

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

        nickname = getIntent().getStringExtra("nicknameGame");
        tv_wellComeTitle.setText(getResources().getString(R.string.TV_wellCome).replace("@NaMe@", nickname));
        oldScore = Integer.valueOf(getIntent().getStringExtra("oldScore"));

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
                        et_letterToWrite.getText().clear();
                        et_letterToWrite.setError(getResources().getString(R.string.errorLetter));
                    }else{
                        char leter= et_letterToWrite.getText().toString().charAt(0);
                        addLetter(leter);

                        if(wordPlayer.equals(wordResult)){
                            wordFound();
                        }
                        et_letterToWrite.getText().clear();
                    }
                }
            }
        });

        b_addWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWroted = et_wordToWrite.getText().toString();
                if(!strWroted.equals("") ){
                    if( !wordResult.equals(strWroted)){
                        et_wordToWrite.getText().clear();
                        wordNotFound();
                    }else{
                        tv_output.setText(wordResult);
                        wordFound();
                    }
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBarAddTitle);
    }

    private void wordFound(){
        int lenght = wordResult.length();
        double resta = (lenght-counterInputs);
        double divisio = resta / lenght;
        int punts = (int) Math.round(divisio * 10);
        if(punts < 0)
            punts = 0;
        sendResult(punts, getResources().getString(R.string.WordFound).replace("@pArAuLa@", wordResult));
    }

    private void wordNotFound(){
        sendResult(0, getResources().getString(R.string.WordNotFound).replace("@pArAuLa@", wordResult));
    }

    private void sendResult(int punts, String mesage){
        Intent intent = new Intent();
        intent.putExtra("playing","true");
        intent.putExtra("nickname",nickname);
        intent.putExtra("intents",String.valueOf(counterInputs));
        intent.putExtra("punts",String.valueOf(punts));
        intent.putExtra("oldScore",String.valueOf(oldScore));
        intent.putExtra("missatge",mesage);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {

        Intent intent = new Intent();
        intent.putExtra("playing", "true");
        intent.putExtra("punts",String.valueOf(0));
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
                            if(strResponse.contains(" ")){
                                getWord();
                                return;
                            }
                            wordResult = strResponse;
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
        String aux="";
        wordPlayer = "";
        for(int i = 0;i<numberLetters(wordResult);i++){
            aux += " _";
            wordPlayer+="_";
        }
        aux += " ";
        tv_output.setText(numberLetters(wordResult)+": "+aux);
    }

    private void addLetter(char letter){

        StringBuilder myString = new StringBuilder(wordPlayer);
        String aux = "";
        for(int i = 0;i<numberLetters(wordResult);i++){
            char strchar = wordResult.charAt(i);
            if(strchar == letter){
                myString.setCharAt(i,letter);
            }

            if(myString.charAt(i)!='_'){
                aux += " "+myString.charAt(i);
            }
            else
            {
                aux += " _";
            }
        }
        aux += " ";
        wordPlayer = myString.toString();
        tv_output.setText(numberLetters(wordResult)+": "+aux);
    }
}