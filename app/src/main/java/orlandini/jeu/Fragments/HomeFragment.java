package orlandini.jeu.Fragments;


import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import orlandini.jeu.R;

/**
 * Activité ouverte au démarrage de l'application.
 *
 * @author Nicolas Orlandini
 * @version 2016.0.46
 *
 * Date de création : 09/10/2016
 * Dernière modification : 05/11/2016
 */

public class HomeFragment extends Fragment {

    String color;
    String nomJoueur;

    public HomeFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_home, container, false);

        chargerPreferences();

        ImageView logo = (ImageView) myView.findViewById(R.id.homeLogo);
        if (color.equals("#EE7600"))
            logo.setBackgroundResource(R.drawable.android_pumpkin);
            //sinon on met l'image POW
        else if (color.equals("#3f51b5"))
            logo.setBackgroundResource(R.drawable.android_bat);

        TextView tv = (TextView) myView.findViewById(R.id.Accueil_nom_joueur);
        String titre = getString(R.string.txt_Bonsoir) + nomJoueur;
        tv.setText(titre);

        Button play = (Button) myView.findViewById(R.id.btnJouer);
        Button playBonus = (Button) myView.findViewById(R.id.btnBonus);
        configurerRippleEffect(play, true);
        configurerRippleEffect(playBonus, false);

        return myView;
    }

    private int obtenirCouleur() {
        return Color.parseColor(color);
    }

    private void chargerPreferences() {
        //SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        //SharedPreferences prefs =  getPreferences(Context.MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        color = prefs.getString("pref_theme", "#FFA500");
        nomJoueur = prefs.getString("id_joueur", "Veuillez configurer les préférences pour la première utlisation");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void configurerRippleEffect(Button play, boolean setFilter) {
        RippleDrawable rippleDrawable = (RippleDrawable)play.getBackground();

        int[][] states = new int[][] {
                new int[]{android.R.attr.state_pressed}
        };

        int[] colors = new int[] {
                obtenirCouleur()
        };

        ColorStateList colorStateList = new ColorStateList(states, colors);
        rippleDrawable.setColor(colorStateList);
        if (setFilter)
            rippleDrawable.setColorFilter(obtenirCouleur(), PorterDuff.Mode.MULTIPLY);
    }
}
