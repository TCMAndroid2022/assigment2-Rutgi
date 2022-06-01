package cat.tecnocampus.mobileapps.practica2.francescboixaburrad.rogerrodriguezmendez.claudiomaioranaarvelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    List<UserWithGames> dades;
    OnRVtodoListener onRVtodoListener;

    public TodoAdapter(List<UserWithGames> _dades, OnRVtodoListener _onRVtodoListener) {
        dades = _dades;
        onRVtodoListener = _onRVtodoListener;
    }
    public void refreshData(List<UserWithGames> _dades){
        dades = _dades;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onRVtodoListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        UserWithGames currentTodo = dades.get(position);
        holder.tv_nickname.setText(currentTodo.user.getNickName() +" - ");
        holder.tv_puntuacio.setText(currentTodo.user.getTotalScore()+", ");
        holder.tv_numero_partides.setText(String.valueOf(currentTodo.user.getNumMach()));
    }

    @Override
    public int getItemCount() {
        return dades.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tv_nickname, tv_puntuacio, tv_numero_partides;
        public OnRVtodoListener onRVtodoListener;

        public ViewHolder(@NonNull View _itemView, OnRVtodoListener _onRVtodoListener) {
            super(_itemView);

            tv_nickname = _itemView.findViewById(R.id.TV_nickname);
            tv_puntuacio = _itemView.findViewById(R.id.TV_puntuacio);
            tv_numero_partides = _itemView.findViewById(R.id.TV_numero_partides);
            onRVtodoListener = _onRVtodoListener;

            _itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRVtodoListener.onRVtodoListener(getBindingAdapterPosition());
        }
    }

    public interface OnRVtodoListener{
        void onRVtodoListener(int posicio);
    }
}
