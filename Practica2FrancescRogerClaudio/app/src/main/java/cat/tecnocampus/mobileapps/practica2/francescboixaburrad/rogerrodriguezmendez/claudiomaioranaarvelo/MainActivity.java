package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView tv_output;
    Button b_getRand;
    RequestQueue rqueue;
    String urlRandom = "https://palabras-aleatorias-public-api.herokuapp.com/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_output = findViewById(R.id.TV_output);
        b_getRand = findViewById(R.id.B_getword);
        rqueue = Volley.newRequestQueue(getApplicationContext());


        b_getRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWord();
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
                            tv_output.setText(strResponse);

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
}