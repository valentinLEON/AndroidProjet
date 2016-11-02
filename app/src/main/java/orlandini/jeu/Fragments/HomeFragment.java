package orlandini.jeu.Fragments;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import orlandini.jeu.R;

/**
 * Activité ouverte au démarrage de l'application.
 *
 * @author Nicolas Orlandini
 * @version 2016.0.44
 *
 * Date de création : 09/10/2016
 * Dernière modification : 02/11/2016
 */

public class HomeFragment extends Fragment {

    SharedPreferences prefs;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        //SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        //SharedPreferences prefs =  getPreferences(Context.MODE_PRIVATE);
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        TextView tv = (TextView) myView.findViewById(R.id.Accueil_nom_joueur);
        String titre = getString(R.string.txt_Bonsoir) + prefs.getString("id_joueur", "Veuillez configurer les préférences pour la première utlisation");
        tv.setText(titre);

        Button play = (Button) myView.findViewById(R.id.btnJouer);
        //play.setColorFilter(changerCouleur(), PorterDuff.Mode.SRC_IN);
        play.setBackgroundColor(changerCouleur());

        return myView;
    }

    private int changerCouleur() {
        String color = prefs.getString("pref_theme", "#FFA500");
        return Color.parseColor(color);
    }
}
