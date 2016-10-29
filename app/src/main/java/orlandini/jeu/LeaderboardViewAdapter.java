package orlandini.jeu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Singu_Admin on 29/10/2016.
 */

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {

    public Integer[] tableaudemerde;

    public LeaderboardViewAdapter(Integer[] unBonGrosTableauDeMerde)
    {
        this.tableaudemerde = unBonGrosTableauDeMerde;
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        LeaderboardViewHolder lvh = new LeaderboardViewHolder(view);
        return lvh;
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
        holder.getmTextViewView().setText(" Mon score : " + String.valueOf(tableaudemerde[position]));
    }

    @Override
    public int getItemCount() {
        return tableaudemerde.length;
    }
}
