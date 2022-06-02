package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder>{

    List<Game> dades;
    private Context context;

    public GameAdapter(List<Game> _dades) {
        dades = _dades;
    }
    public void refreshData(List<Game> _dades, Context context){
        dades = _dades;
        notifyDataSetChanged();
        this.context = context;

    }


    @NonNull
    @Override
    public GameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowgame, parent, false);
        GameAdapter.ViewHolder viewHolder = new GameAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameAdapter.ViewHolder holder, int position) {
        Game currentTodo = dades.get(position);
        holder.tv_idGame.setText(context.getResources().getString(R.string.idStringGame)
                                                            .replace("@iD@", String.valueOf(currentTodo.gameid)));
        holder.tv_puntuacioGame.setText("\t"+context.getResources().getString(R.string.scoreStringGame)
                                                .replace("@pUnTs@", String.valueOf(currentTodo.scoregame)));
    }

    @Override
    public int getItemCount() {
        return dades.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_idGame, tv_puntuacioGame;

        public ViewHolder(@NonNull View _itemView) {
            super(_itemView);

            tv_idGame = _itemView.findViewById(R.id.TV_idGame);
            tv_puntuacioGame = _itemView.findViewById(R.id.TV_puntuacioGame);
        }

        @Override
        public void onClick(View view) { }
    }
}
