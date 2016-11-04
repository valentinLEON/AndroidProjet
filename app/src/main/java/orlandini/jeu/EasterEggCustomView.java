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
    private int vitesseY;
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

    private Runnable animatorY = new Runnable() {
        @Override
        public void run() {
            updateY();
            invalidate();
            postDelayed(this, vitesseY);
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

        bitmapDelorean = BitmapFactory.decodeResource(res, R.drawable.flying_delorean);
        chargerBitmap(res);
        parametrerImagePerso(res);
        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);

        vitesseY = 300;
        vitesse = 7;
        score = 0;
        mFileX = screenWidth;
        mFileY = screenHeight/2;
        mDmcX = screenWidth/2;
        mDmcY = screenHeight/2;

        super.setOnTouchListener(this);
        removeCallbacks(animator);
        post(animator);
        removeCallbacks(animatorY);
        post(animatorY);
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
            // Ceci est affiché lorsque le jeu n'a pas commencé
            mFileX = screenWidth;
            mFileY = screenHeight/2;
            mDmcX = screenWidth/2;
            mDmcY = screenHeight/2;
            setVisibility(View.INVISIBLE);
            estTouche = false;
            canvas.drawText("Evitez les fantômes pour gagner des points", 50, 250, paint);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        // Si le jeu n'est pas en pause
        if (!GameActivity.getPaused()) {
            int action = motionEvent.getAction();
            // on récupère la position du doigt
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    return true;

                case MotionEvent.ACTION_MOVE:
                    // On déplace la delorean à l'endroit ou se trouve le doigt de l'utilisateur
                    int ICON_SIZE = 350;
                    mDmcX = x - ICON_SIZE;
                    mDmcY = y - 200;
                    if (!estTouche) {
                        // Si la delorean touche le personnage, sa position se réinitialise, l'utilisateur n'a pas de points
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
                if (mFileX != 0) {
                    mFileX -= 10;
                }
                else {
                    // Si le personnage a pu aller jusqu'au point 0 c'est que la delorean ne l'a pas
                    // touché, l'utilisateur récupère un point et la postion du personnage se réinitialise
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

    /**
     * Mise à jour aléatoire de la position Y du personnage
     */
    public void updateY() {
        if (!GameActivity.getPaused() && GameActivity.isGame()){
            Random randomValue = new Random();
            float randomDirection = (float) randomValue.nextInt(2);
            // Tant que l'utilisateur n'a pas touché le personnage
            // Et que le personnage n'a pas atteint la gauche de l'acran
            if (!estTouche && mFileX != 0) {
                // On modifie aléatoirement la position y du personnage
                if (randomDirection < 1)
                    mFileY -= 120;
                else
                    mFileY += 120;
            }
        }
    }

    /**
     * Réinitialisation de la position du personnage (coin droit et Y aléatoire)
     */
    private void reinitialiserPositionPerso() {
        Random randomValue = new Random();
        mFileY = (float) randomValue.nextInt(screenHeight - 120);
        mFileX = screenWidth;
    }
}
