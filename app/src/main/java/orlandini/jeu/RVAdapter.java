package orlandini.jeu;


import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Singu_Admin on 23/10/2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ScoreViewHolder> {

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ScoreViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView score;

        ScoreViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view_scoreboard);
            score = (TextView)itemView.findViewById(R.id.score);
        }
    }
}
