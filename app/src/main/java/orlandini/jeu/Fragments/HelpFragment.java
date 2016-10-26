package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import orlandini.jeu.R;


/**
 * Ce fragment affiche l'aide pour l'utilisateur
 *
 * @author Nicolas Orlandini
 * @version 2016.0.34
 *
 * Nom : HelpFragment.java
 * Date de création : 24/10/2016
 * Dernière modification : 25/10/2016
 */

public class HelpFragment extends Fragment {


    public HelpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

}
