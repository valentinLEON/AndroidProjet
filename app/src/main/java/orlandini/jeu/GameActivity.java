package orlandini.jeu;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity{
    public static final String PREFS_NAME = "preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences mPrefs = getSharedPreferences(PREFS_NAME, 0);
        int vitesse = mPrefs.getInt(getString(R.string.title_seekbar_vitesse), 500);

        Toast.makeText(getApplicationContext(), "Vitesse : " + String.valueOf(vitesse),Toast.LENGTH_LONG).show();

    }
}
