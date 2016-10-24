package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import orlandini.jeu.GameCustomView;
import orlandini.jeu.R;
import orlandini.jeu.ScoreDataBase;

import static orlandini.jeu.MainActivity.scoreDataBase;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    private TextView score;

    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        score = (TextView)myView.findViewById(R.id.score);
        /*foreach {
            Toast.makeText(znfzoifher);
        }*/

        return myView;
    }

}
