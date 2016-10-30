package orlandini.jeu.Fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import orlandini.jeu.MainActivity;
import orlandini.jeu.R;

/**
 * Ce fragment affiche le A propos de l'application
 * - La version
 * - Les auteurs
 * - Une courte description
 *
 * @author Nicolas Orlandini
 * @version 2016.0.41
 *
 * Nom ! AProposFragment.java
 * Date de création : 25/10/2016
 * Dernière modification : 30/10/2016
 */

public class AProposFragment extends Fragment {

    ListView mListView = null;
    CardView mCardView = null;
    private SharedPreferences prefs;

    String[] ressources = new String[]{
                "developer.android.com",
                "youtube.com",
                "tutos-android-france.com/"
    };

    public AProposFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutView = inflater.inflate(R.layout.fragment_apropos, container, false);

        mCardView = (CardView) aboutView.findViewById(R.id.cvAbout);
        mCardView.setCardBackgroundColor(changerCouleur());

        mListView = (ListView) aboutView.findViewById(R.id.listViewRessources);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1, ressources
        );

        mListView.setAdapter(adapter);

        return aboutView;
    }

    private int changerCouleur() {
        // Récupération des préférences
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String color = prefs.getString("pref_theme", "#00AFF0");
        return Color.parseColor(color);
    }

}
