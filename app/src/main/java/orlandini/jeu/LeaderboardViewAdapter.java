package orlandini.jeu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Singu_Admin on 29/10/2016.
 */

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {

    private Context mCtx;

    private Integer[] tableaudemerde;

    public LeaderboardViewAdapter(Integer[] unBonGrosTableauDeMerde)
    {
        this.tableaudemerde = unBonGrosTableauDeMerde;
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
        if(tableaudemerde[position] == null){
            holder.getmTextViewView().setText(">");
            holder.getmTextViewView().setTextSize(30);
            holder.getmImageView().setImageResource(R.drawable.delorean1);
        }else
            holder.getmTextViewView().setText(" Score : " + String.valueOf(tableaudemerde[position]));

        if(position==0)
            holder.getmImageView().setBackgroundColor(Color.parseColor("#ffd700"));
        else if(position==1)
            holder.getmImageView().setBackgroundColor(Color.parseColor("#CECECE"));
        else if(position==2)
            holder.getmImageView().setBackgroundColor(Color.parseColor("#cd7f32"));
    }

    @Override
    public int getItemCount() {
        return tableaudemerde.length;
    }
}
