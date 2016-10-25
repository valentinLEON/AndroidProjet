package orlandini.jeu;


import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Singu_Admin on 23/10/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ScoreViewHolder> {

    public ScoreDataBase db;
    ArrayList<String> myScore;

    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView score;

        ScoreViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view_scoreboard);
            score = (TextView)itemView.findViewById(R.id.score);
        }
    }

    public RVAdapter (ArrayList<String> score){
        myScore = new ArrayList<String>();
        myScore = score;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ScoreViewHolder svh = new ScoreViewHolder(v);
        return svh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        holder.score.setText(myScore.get(position));
    }

    @Override
    public int getItemCount() {
        return myScore.size();
    }
}
