package orlandini.jeu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class GameFragment extends Fragment {

    private int vitesse = 1000;
    private Button StartButton;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private ScoreDataBase scoreDB;

    public static int getSecs() {
        return secs;
    }

    static int secs = 0;

    public static int getMins() {
        return mins;
    }

    static int mins = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_game, container, false);

        Intent game = new Intent(getContext(), GameCustomView.class);
        game.putExtra("vitesse", vitesse);

        scoreDB = new ScoreDataBase(getContext());

        StartButton = (Button) myView.findViewById(R.id.startButton);

        StartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                GameCustomView.setScore(0);
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });

        return myView;
    }

    public void changerVitesse(int myVitesse){
        //TextView myTextView = (TextView) myView.findViewById(R.id.);
        //myTextView.setText(Integer.toString(myNumberPicker));

        //GameCustomView gameView = new GameCustomView(this.getContext());
        //gameView.changeTextNumber(myVitesse);

        //((GameCustomView)getView()).setVitesse(myVitesse);

        Intent game = new Intent(getContext(), GameCustomView.class);
        game.putExtra("vitesse", myVitesse);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            if (secs == 10) {
                secs = 0;
                //add in the database
                scoreDB.addScore(GameCustomView.getScore());

                FragmentManager fm = getFragmentManager();
                FatalityDialogFragment newFragment = new FatalityDialogFragment();
                newFragment.show(fm, "Fragment_fatality_dialog");
                customHandler.removeCallbacks(this);
                StartButton.setVisibility(View.VISIBLE);
                //Toast.makeText(getContext(), String.valueOf(scoreDB.getAllScores()),Toast.LENGTH_LONG).show();
            }
            else {
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
}