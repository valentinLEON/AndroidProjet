package orlandini.jeu.Fragments;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import orlandini.jeu.R;


public class AdvancedSettingFragment extends PreferenceFragment {

    public AdvancedSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
