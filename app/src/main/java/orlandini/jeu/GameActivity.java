package orlandini.jeu;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
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
 * @version 2016.0.39
 *
 * Date de création : 09/10/2016
 * Dernière modification : 29/10/2016
 */

public class GameActivity extends AppCompatActivity{

    private Button StartButton;
    private Handler customHandler = new Handler();
    private ScoreDataBase scoreDB;
    private Toolbar toolbar;
    private Fragment fragment = null;
    private SharedPreferences prefs;
    private MyCount counter = null;

    private boolean recommencer = false;
    private String temps = null;
    private long s1 = 0;

    public static boolean getPaused() {
        return isPaused;
    }
    private static boolean isPaused = false;

    public static boolean isGame() {
        return isGame;
    }

    private static boolean isGame = false;

    public static int getSecs() {
        return secs;
    }
    private static int secs = 0;


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
        temps = prefs.getString("pref_temps_jeu", "30");
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

                counter = new MyCount((Integer.parseInt(temps)+1)*1000, 1000);
                counter.start();
                isGame = true;
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });
    }

    /**
     * Runnable permettant de gérer la partie
     */
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            // L'utilisateur souhaite recomencer la patie
            if (recommencer) {
                reinitialiserJeu();
            }
            // Le jeu est en cours
            else {
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
        reinitialiserJeu();
        NavUtils.navigateUpFromSameTask(this);
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
                reinitialiserJeu();
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
                pause(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Gestion du menu pause/play avec arrêt et redémarrage du rennable
     *
     * @param item item sélectionné
     */
    private void pause(MenuItem item) {
        if (isPaused) {
            isPaused = false;
            // On redémarre le runnable
            customHandler.postDelayed(updateTimerThread, 0);
            /** On créer un nouveau timer qui commence au temps ou l'ancien
             * timer a été arrêté.
             */
            counter= new MyCount(s1, 1000);
            counter.start();

            // On change l'icon correspondant à l'item
            item.setIcon(R.drawable.ic_pause);
        }
        else {
            isPaused = true;
            // On stop le runnable
            customHandler.removeCallbacks(updateTimerThread);
            // On récupère les millisecondes écoulées depuis le boot
            counter.cancel();
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


    public class MyCount extends CountDownTimer {
        private MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            //Ajouter le score dans la base de données
            scoreDB.addScore(GameCustomView.getScore());

            FragmentManager fm = getSupportFragmentManager();
            FatalityDialogFragment newFragment = new FatalityDialogFragment();
            newFragment.show(fm, "Fragment_fatality_dialog");
            customHandler.removeCallbacks(updateTimerThread);
            StartButton.setVisibility(View.VISIBLE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            s1 = millisUntilFinished;
            long mill = millisUntilFinished / 1000;
            secs = (int) mill - 1;
        }
    }

    public void reinitialiserJeu(){
        recommencer = false;
        isGame = false;
        if (counter != null) {
            counter.cancel();
        }
        secs = Integer.parseInt(temps);

        customHandler.removeCallbacks(updateTimerThread);
        StartButton.setVisibility(View.VISIBLE);
    }
}
