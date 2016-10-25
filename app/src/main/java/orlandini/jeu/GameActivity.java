package orlandini.jeu;


import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Auteur : Nicolas Orlandini
 * Date de création : 09/10/2016
 * Dernière modification : 25/10/2016
 */

public class GameActivity extends AppCompatActivity{

    private Button StartButton;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private ScoreDataBase scoreDB;
    private Toolbar toolbar;

    Fragment fragment = null;

    public static int getSecs() {
        return secs;
    }

    static int secs = 0;

    public static int getMins() {
        return mins;
    }

    static int mins = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Jeu");
        }

        scoreDB = new ScoreDataBase(getApplicationContext());

        StartButton = (Button) findViewById(R.id.startButton);
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

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (secs == 30) {
                secs = 0;
                //add in the database
                scoreDB.addScore(GameCustomView.getScore());

                FragmentManager fm = getSupportFragmentManager();
                FatalityDialogFragment newFragment = new FatalityDialogFragment();
                newFragment.show(fm, "Fragment_fatality_dialog");
                customHandler.removeCallbacks(this);
                StartButton.setVisibility(View.VISIBLE);
            } else {
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                secs = (int) (updatedTime / 1000);
                mins = secs / 60;
                secs = secs % 60;

                customHandler.postDelayed(this, 0);
                StartButton.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public void onBackPressed()
    {
        System.exit(0);
        //super.onBackPressed();// optional depending on your needs
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_jeu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.preferences:
                Class fragmentClass =  SettingFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.game_content, fragment).commit();
                break;
            case R.id.new_game:
                Toast.makeText(this, "Nouveau jeu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
