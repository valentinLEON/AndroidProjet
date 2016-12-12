package orlandini.jeu.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.IntentCompat;
import android.widget.TextView;

import orlandini.jeu.MainActivity;
import orlandini.jeu.R;

/**
 * Gestion des préférences utilisateur
 *
 * @author Nicolas Orlandini
 * @version 2016.0.47
 *
 * Date de création : 09/10/2016
 * Dernière modification : 05/11/2016
 */

public class AdvancedSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        AfficherValuePref(findPreference("id_joueur"));
        AfficherValuePref(findPreference("pref_temps_jeu"));
        AfficherValuePref(findPreference("pref_theme"));
        AfficherValuePref(findPreference("pref_son"));
        AfficherValuePref(findPreference("pref_perso"));

        // Listener permettant de détecter si la value change
        // Si la value change l'activité principale redémarre pour appliquer le thème
        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {

                getActivity().finish();
                final Intent intent = getActivity().getIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().startActivity(intent);
                return true;
            }
        };
        // Le listener est appliqué à la préférence du thème
        findPreference("pref_theme").setOnPreferenceChangeListener(listener);

        // Ecouteur d'évenement permettant de modifier le nom du joueur dans le navigation drawer
        Preference.OnPreferenceChangeListener listenerNomJoueur = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object value) {

                NavigationView nav = MainActivity.getNvDrawer();
                TextView txtNomJoueur = (TextView) nav.findViewById(R.id.nom_joueur);
                txtNomJoueur.setText(value.toString());
                return true;
            }
        };
        // Le listener est appliqué à la préférence du nom du joueur
        findPreference("id_joueur").setOnPreferenceChangeListener(listenerNomJoueur);

        // affichage de la valeur de la vitesse dans le titre
         findPreference("seekbar_vitesse").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.valueOf(String.valueOf(newValue));
                preference.setTitle(String.format("Vitesse : %d", progress));
                return true;
            }
        });
    }

    /**
     * Permet d'afficher la valeur de la préférence dans le sommaire de la préférence passée en
     * paramètre
     * Exemple :
     *          Titre : Thème
     *          Sommaire : Halloween
     *
     * @param preference préference sur laquelle appliquer le sommaire
     */
    private void AfficherValuePref(Preference preference) {
        // Définir le listener pour vérifier si une valeur change
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Déclancher l'écouteur d'évènement pour la préférence passée en paramètre
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    private Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // Pour les ListPreferences, il faut déterminer quel index est sélectionné
                // dans la liste avant de l'afficher dans le sommaire de la préférence.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else {
                // Pour les autres préférences, définir le sommaire avec la valeur correspondante
                preference.setSummary(stringValue);
            }
            return true;
        }
    };
}
