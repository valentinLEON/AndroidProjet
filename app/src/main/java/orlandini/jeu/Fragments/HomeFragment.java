package orlandini.jeu.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.Iterator;

import orlandini.jeu.GameActivity;
import orlandini.jeu.R;
import orlandini.jeu.ScoreDataBase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        //SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        //SharedPreferences prefs =  getPreferences(Context.MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        TextView tv = (TextView) myView.findViewById(R.id.Accueil_nom_joueur);
        String titre = getString(R.string.txt_Bonsoir) + prefs.getString("id_joueur", "Veuillez configurer les préférences pour la première utlisation");
        tv.setText(titre);

        mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.start_windows);
        //mMediaPlayer.start();

        Button tryAgain = (Button) myView.findViewById(R.id.btn_TryAgain);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameActivity.class);
                startActivity(intent);
            }
        });


        return myView;
    }
}
