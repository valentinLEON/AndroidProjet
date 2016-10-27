package orlandini.jeu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Singu_Admin on 27/10/2016.
 */

public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.ViewHolder> {

    private ArrayList<String> items;
    private int itemLayout;

    public RecyclerSimpleViewAdapter(ArrayList<String> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get inflater and get view by resource id itemLayout
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        // return ViewHolder with View
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerSimpleViewAdapter.ViewHolder holder, int position) {
        String item = items.get(position);
        // save information in holder, we have one type in this adapter
        holder.primaryText.setText(item);
        holder.itemView.setTag(item);
        if ((position % 2) == 0) {
            holder.itemView.setBackgroundResource(R.color.darkOrange);
        } else {
            holder.itemView.setBackgroundResource(R.color.orange);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // TextViex
        public TextView primaryText;
        /**
         * Constructor ViewHolder
         * @param itemView: the itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            // link primaryText
            primaryText = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }
}
