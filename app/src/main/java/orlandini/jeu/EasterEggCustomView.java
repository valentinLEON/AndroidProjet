package orlandini.jeu;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Niveau caché easter egg : Back To The Future
 * Cette vue personnalisée permet d'afficher le jeu et gère les
 * interactions ainsi que l'affichage du timer et du score
 *
 * @author Nicolas Orlandini
 * @version 2016.0.45
 *
 * Date de création : 26/10/2016
 * Dernière modification : 04/11/2016
 */

public class EasterEggCustomView extends Jeu implements View.OnTouchListener  {

    private float mFileX;
    private float mFileY;
    private float mDmcX;
    private float mDmcY;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        EasterEggCustomView.score = score;
    }

    private int vitesse;
    private boolean estTouche = false;

    private Bitmap bitmapDelorean;
    private Vibrator vibrator;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            update();
            invalidate();
            postDelayed(this, vitesse);
        }
    };

    private Runnable animDelorean = new Runnable() {
        @Override
        public void run() {
            invalidate();
            postDelayed(this,8);
        }
    };

    public void init () {
        Resources res = getResources();

        recupererPreferences(this.getContext());

        parametrerSonPerso();

        bitmapDelorean = BitmapFactory.decodeResource(res, R.drawable.delorean1);
        chargerBitmap(res);
        parametrerImagePerso(res);
        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);

        vitesse = 10;
        score = 0;
        mFileX = screenWidth;
        mFileY = screenHeight/2;
        mDmcX = screenWidth/2;
        mDmcY = screenHeight/2;

        super.setOnTouchListener(this);
        removeCallbacks(animator);
        post(animator);
        removeCallbacks(animDelorean);
        post(animDelorean);
    }

    public EasterEggCustomView(Context context) {
        super(context);
        init();
    }

    public EasterEggCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        afficherInfosJeu(canvas);

        if (GameActivity.isGame()) {
            canvas.drawBitmap(bitmapDelorean, mDmcX, mDmcY, null);
            if (isInvisible)
                canvas.drawBitmap(bitmapPerso, mFileX, mFileY, null);
            else
                canvas.drawBitmap(bitmaptoucher, mFileX, mFileY, null);
        }
        else {
            mFileX = screenWidth;
            mFileY = screenHeight/2;
            mDmcX = screenWidth/2;
            mDmcY = screenHeight/2;
            setVisibility(View.INVISIBLE);
            estTouche = false;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!GameActivity.getPaused()) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    return true;

                case MotionEvent.ACTION_MOVE:
                    int ICON_SIZE = 350;
                    mDmcX = x - ICON_SIZE;
                    mDmcY = y - 200;
                    if (!estTouche) {
                        if (mDmcX + ICON_SIZE >= mFileX - 300 && mDmcX <= mFileX + 100 && mDmcY + 100 >= mFileY
                                && mDmcY + 100 <= mFileY + 91) {
                            if (prefs.getBoolean("switch_sons", true))
                                mMediaPlayer.start();
                            if (prefs.getBoolean("switch_vibreur", true))
                                vibrator.vibrate(100);
                            setVisibility(View.VISIBLE);
                            estTouche = true;
                            invalidate();
                        }
                    }

                    return true;
                case MotionEvent.ACTION_UP:
                    estTouche = false;
                default:
                    return false;
            }
        }
        else
            return false;
    }

    public void update() {
        if (!GameActivity.getPaused() && GameActivity.isGame()){

            if (!estTouche) {
                if (mFileX != 0)
                    mFileX -= 10;
                else {
                    score++;
                    if (vitesse > 0)
                        vitesse -= 1;
                    reinitialiserPositionPerso();
                }
            }
            else {
                reinitialiserPositionPerso();
                setVisibility(View.INVISIBLE);
                estTouche = false;
                if (vitesse > 0)
                    vitesse -= 1;
            }
        }
        if (!GameActivity.isGame()) {
            reinitialiserPositionPerso();
            removeCallbacks(animator);
            removeCallbacks(animDelorean);
        }
    }

    private void reinitialiserPositionPerso() {
        Random randomValue = new Random();
        mFileY = (float) randomValue.nextInt(screenHeight - 120);
        mFileX = screenWidth;
    }
}
