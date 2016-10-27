package orlandini.jeu;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Cette activité gère l'affichage de la durée d'une partie,
 * contient la custom view du jeu,
 * initialise l'action bar
 * Gère les actions liées à l'action bar.
 *
 * @author Nicolas Orlandini
 * @version 2016.0.34
 *
 * Date de création : 09/10/2016
 * Dernière modification : 27/10/2016
 */

public class GameActivity extends AppCompatActivity{

    private Button StartButton;
    private Handler customHandler = new Handler();

    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private long startPauseTime = 0L;
    private long pauseTime = 0L;
    private boolean recommencer = false;

    private static boolean isPaused = false;
    public static boolean getPaused() {
        return isPaused;
    }

    private ScoreDataBase scoreDB;
    private Toolbar toolbar;
    private Fragment fragment = null;
    private SharedPreferences prefs;

    public static int getSecs() {
        return secs;
    }
    private static int secs = 0;

    public static int getMins() {
        return mins;
    }
    private static int mins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(changerCouleur()));
        }

        scoreDB = new ScoreDataBase(getApplicationContext());

        StartButton = (Button) findViewById(R.id.startButton);
        StartButton.setBackgroundDrawable(new ColorDrawable(changerCouleur()));
        StartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*Class fragmentClass =  GameCustomView.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.game_content, fragment).commit();*/
                GameCustomView.setScore(0);
                startTime = SystemClock.uptimeMillis();

                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
    }

    /**
     * Runnable permettant de gérer la partie
     */
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            String temps = prefs.getString("pref_temps_jeu", "30");

            // Le temps défini par l'utilisateur est écoulé
            if (secs == Integer.parseInt(temps)) {
                // Réinitialiser le timer
                secs = 0;
                pauseTime = 0L;
                //AJouter le score dans la base de données
                scoreDB.addScore(GameCustomView.getScore());

                FragmentManager fm = getSupportFragmentManager();
                FatalityDialogFragment newFragment = new FatalityDialogFragment();
                newFragment.show(fm, "Fragment_fatality_dialog");
                customHandler.removeCallbacks(this);
                StartButton.setVisibility(View.VISIBLE);
            }
            // L'utilisateur choisis de recommencer la partie
            else if (recommencer) {
                recommencer = false;
                secs = 0;
                pauseTime = 0L;
                customHandler.removeCallbacks(this);
                StartButton.setVisibility(View.VISIBLE);

            }
            // Le jeu est en cours, le temps s'incrémente
            else {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime - pauseTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                secs = (int) (updatedTime / 1000);
                mins = secs / 60;
                secs = secs % 60;

                customHandler.postDelayed(updateTimerThread, 0);

                StartButton.setVisibility(View.INVISIBLE);
            }
        }
    };

    /**
     * Méthode exécutée lorsque l'utilisateur appuie sur le bouton retour de l'appareil.
     */
    @Override
    public void onBackPressed()
    {
        System.exit(0);
        //super.onBackPressed();// optional depending on your needs
    }

    /**
     * Association du menu et du xml (partie graphique).
     * @param menu action bar
     * @return booléen
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }

    /**
     * Défini les actions a exécuter suivant l'item de l'action bar sélectionné par l'utilisateur.
     * @param item item sélectionné par l'utilisateur
     * @return booléen
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case android.R.id.home:
                // Retour à l'activité précédente
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.preferences:
                // Ouverture des préférences
                Class fragmentClass =  SettingFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.game_content, fragment).commit();
                break;
            case R.id.new_game:
                // L'utilisateur est avertit qu'une nouvelle partie commence
                Toast.makeText(this, "Nouveau jeu", Toast.LENGTH_SHORT).show();
                recommencer = true;
                break;
            case R.id.pause:
                // Gestion du menu pause
                gererPause(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Gestion du menu pause/play avec arrêt et redémarrage du rennable
     *
     * @param item item sélectionné
     */
    private void gererPause(MenuItem item) {
        if (isPaused) {
            isPaused = false;
            // On redémarre le runnable
            customHandler.postDelayed(updateTimerThread, 0);
            // On ajoute les millisecondes écoulées actuellement - les millisecondes écoulées quand la pause a commencé.
            pauseTime += SystemClock.uptimeMillis() - startPauseTime;
            // On change l'icon correspondant à l'item
            item.setIcon(R.drawable.ic_pause);
        }
        else {
            isPaused = true;
            // On stop le runnable
            customHandler.removeCallbacks(updateTimerThread);
            // On récupère les millisecondes écoulées depuis le boot
            startPauseTime = SystemClock.uptimeMillis();
            // On change l'icon correspondant à l'item
            item.setIcon(R.drawable.ic_play);
        }
    }

    private int changerCouleur() {
        // Récupération des préférences
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String color = prefs.getString("pref_theme", "#FFA500");
        return Color.parseColor(color);
    }
}
