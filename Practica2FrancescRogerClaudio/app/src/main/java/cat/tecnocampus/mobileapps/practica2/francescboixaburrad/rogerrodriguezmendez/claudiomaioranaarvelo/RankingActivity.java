package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity implements TodoAdapter.OnRVtodoListener{

    RecyclerView rv_llista;
    TodoAdapter todoAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Todo> dataSet;

    private int indexModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankinga);

        layoutManager = new LinearLayoutManager( this);
        rv_llista.setLayoutManager(layoutManager);

        if(savedInstanceState==null){
            exemple();
            todoAdapter = new TodoAdapter(dataSet, this);
            rv_llista.setAdapter(todoAdapter);
        }
    }


    private void exemple(){
        dataSet = new ArrayList<Todo>();
        Todo td = new Todo("Maio","100","45");
        dataSet.add(td);

        dataSet = new ArrayList<Todo>();
        td = new Todo("Rutgi","5","50");
        dataSet.add(td);

        dataSet = new ArrayList<Todo>();
        td = new Todo("Fboix","0","70");
        dataSet.add(td);

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
        Todo currentTodo = dataSet.get(posicio);
        Intent intent = new Intent(this, InfoPlayerActivity.class);
        intent.putExtra("nomEnviat", currentTodo.getNickname());
        intent.putExtra("tipoEnviat", currentTodo.getPuntuacio());
        intent.putExtra("telefEnviat", currentTodo.getNumero_partides());
        try{
            indexModify = posicio;
            //myActivityResultLauncherInfo.launch(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),  R.string.errorDoing, Toast.LENGTH_LONG).show();
            indexModify = -1;
        }
    }
}