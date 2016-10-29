package orlandini.jeu;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Singu_Admin on 29/10/2016.
 */

public class LeaderboardViewHolder extends RecyclerView.ViewHolder {

    private CardView mCardView;
    private TextView mTextViewView;
    private ImageView mImageView;

    public LeaderboardViewHolder(View itemView) {
        super(itemView);

        mTextViewView = (TextView) itemView.findViewById(R.id.myscore);
        mCardView = (CardView) itemView.findViewById(R.id.card_view_scoreboard);
    }

    public CardView getmCardView() {
        return mCardView;
    }

    public void setmCardView(CardView mCardView) {
        this.mCardView = mCardView;
    }

    public TextView getmTextViewView() {
        return mTextViewView;
    }

    public void setmTextViewView(TextView mTextViewView) {
        this.mTextViewView = mTextViewView;
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }
}
