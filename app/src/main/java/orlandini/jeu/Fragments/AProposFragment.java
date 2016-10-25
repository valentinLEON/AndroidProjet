package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import orlandini.jeu.R;

/**
 * Auteur : Nicolas Orlandini
 * Date de création : 25/10/2016
 * Dernière modification : 25/10/2016
 */

public class AProposFragment extends Fragment {

    public AProposFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apropos, container, false);
    }
}
