package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InfoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_player);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBarAddTitle);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}