package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import orlandini.jeu.MainActivity;
import orlandini.jeu.R;

import orlandini.jeu.ScoreDataBase;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    private TextView myscore;
    private ArrayList<String> score;
    private String myString;
    private RecyclerView rv;

    private ScoreDataBase db = MainActivity.scoreDataBase;

    public LeaderboardFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        rv = (RecyclerView)myView.findViewById(R.id.rv);
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 0; i < items.size(); i++){
            items.add("test " + i);
        }

        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(items, android.R.layout.simple_list_item_1);
        //rv.setAdapter(adapter);
        //rv.setLayoutManager(new LinearLayoutManager(this));



        /*for (Iterator<Integer> i = db.getFiveBestScores().iterator(); i.hasNext();){
            Integer scoremescouilles = i.next();
            Log.v(String.valueOf(scoremescouilles), "mon score");
        }*/

        myString = db.getTopScore();
        myscore = (TextView) myView.findViewById(R.id.myscore);
        myscore.setText(getString(R.string.text_score) + myString);

        return myView;
    }

    private void initializeData() {
        score = new ArrayList<>();
        for (Iterator<String> i = MainActivity.scoreDataBase.getAllScores().iterator(); i.hasNext(); ) {
            String scoremescouilles = i.next();
            score.add(scoremescouilles);
        }
    }

    private void initializeAdapter() {
        /*RVAdapter adapter = new RVAdapter(score);
        rv.setAdapter(adapter);*/
    }

}