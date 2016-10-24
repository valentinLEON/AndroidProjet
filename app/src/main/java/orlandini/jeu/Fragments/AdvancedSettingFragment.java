package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import orlandini.jeu.R;


public class AdvancedSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
