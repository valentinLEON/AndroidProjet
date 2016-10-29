package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * @version 2016.0.34
 *
 * Nom ! AProposFragment.java
 * Date de création : 25/10/2016
 * Dernière modification : 25/10/2016
 */

public class AProposFragment extends Fragment {

    ListView mListView;
    String[] ressources = new String[]{
                "developer.android.com",
                "youtube.com",
                "tutos-android-france.com/"
    };

    public AProposFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View aboutView = inflater.inflate(R.layout.fragment_apropos, container, false);

        mListView = (ListView) aboutView.findViewById(R.id.listViewRessources);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1, ressources
        );

        mListView.setAdapter(adapter);

        return aboutView;
    }
}
