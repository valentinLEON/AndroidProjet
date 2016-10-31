package orlandini.jeu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Singu_Admin on 29/10/2016.
 */

public class LeaderboardViewAdapter extends RecyclerView.Adapter<LeaderboardViewHolder> {

    //variables
    private final Context mCtx; //get le context
    private Integer[] tableaudemerde;

    public LeaderboardViewAdapter(Context context, Integer[] unBonGrosTableauDeMerde)
    {
        this.tableaudemerde = unBonGrosTableauDeMerde;
        this.mCtx = context;
    }

    @Override
    public LeaderboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new LeaderboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LeaderboardViewHolder holder, int position) {
        //ajout de la police
        Typeface typeface = Typeface.createFromAsset(mCtx.getAssets(), "BTTF.ttf");
        //permet de changer les paramètres
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //set les margin des paramètres
        params.setMargins(0, 15, 0, 0);
        //holder.getmCardView().setAlpha(0.6f);

        holder.getmTextViewView().setTextSize(20);
        //suivant la position
        if(position==0) {
            if(holder.getmCardView() != null){
                holder.getmCardView().setCardBackgroundColor(Color.parseColor("#ffd700"));
            }
            //holder.getmImageView().setImageResource(R.drawable.ic_gold);
            holder.getmTextViewView().setTypeface(typeface);
            holder.getmTextViewView().setText("1\n\nScore : " + String.valueOf(tableaudemerde[position]));
            holder.getmTextViewView().setLayoutParams(params);
        }
        else if(position==1) {
            if(holder.getmCardView() != null){
                holder.getmCardView().setCardBackgroundColor(Color.parseColor("#CECECE"));
            }
            //holder.getmImageView().setImageResource(R.drawable.ic_silver);
            holder.getmTextViewView().setTypeface(typeface);
            holder.getmTextViewView().setText("2\n\nScore : " + String.valueOf(tableaudemerde[position]));
            holder.getmTextViewView().setLayoutParams(params);

        }
        else if(position==2) {
            if(holder.getmCardView() != null){
                holder.getmCardView().setCardBackgroundColor(Color.parseColor("#cd7f32"));
            }
            //holder.getmImageView().setImageResource(R.drawable.ic_bronze);
            holder.getmTextViewView().setTypeface(typeface);
            holder.getmTextViewView().setText("3\n\nScore : " + String.valueOf(tableaudemerde[position]));
            holder.getmTextViewView().setLayoutParams(params);
        }
        else if(tableaudemerde[position] == null) {
            holder.getmTextViewView().setTypeface(typeface);
            holder.getmTextViewView().setText(">");
            holder.getmTextViewView().setTextSize(30);
            holder.getmImageView().setImageResource(R.drawable.delorean1);
        }
        else {
            holder.getmTextViewView().setTextSize(10);
            holder.getmTextViewView().setTypeface(typeface);
            holder.getmTextViewView().setText(" Score : " + String.valueOf(tableaudemerde[position]));
            //holder.getmImageView().setImageResource(R.drawable.ic_trophee);
            holder.getmTextViewView().setLayoutParams(params);
        }
    }

    /**
     * On récupère le nombre d'item
     * @return
     */
    @Override
    public int getItemCount() {
        return tableaudemerde.length;
    }
}
