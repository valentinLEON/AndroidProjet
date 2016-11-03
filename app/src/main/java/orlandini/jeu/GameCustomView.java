package orlandini.jeu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import java.util.Random;

/**
 * Cette vue personnalisée permet d'afficher le jeu et gère les
 * interactions ainsi que l'affichage du timer et du score
 *
 * @author Nicolas Orlandini
 * @version 2016.0.42
 *
 * Date de création : 09/10/2016
 * Dernière modification : 03/11/2016
 */

public class GameCustomView extends View implements View.OnTouchListener {

    private final int IMAGE_SIZE = 180;

    private float mFileX;
    private float mFileY;
    private int screenWidth;
    private int screenHeight;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameCustomView.score = score;
    }

    private static int score = 0;
    private int prefVitesse = 500;

    boolean isInvisible = true;

    private Bitmap bitmapPerso;
    private Bitmap bitmapPow;
    private Bitmap bitmapRip;
    private Bitmap bitmapTemps;
    private Bitmap bitmapChat;
    private Paint paint;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;
    private String color;
    private int perso;
    private int son;
    private SpriteAnimation spriteAnimation;

    SharedPreferences prefs;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            update();
            invalidate();
            int tempsAttente = 0;
            if (prefVitesse != 0)
                tempsAttente = 100000/prefVitesse;
            else
                tempsAttente = 6000;
            postDelayed(this, tempsAttente);
        }
    };

    //initialisation du jeu
    public void init () {
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        Resources res = getResources();
        //setBackgroundResource(R.drawable.halloween_wallpaper);

        recupererPreferences();

        bitmapPow = BitmapFactory.decodeResource(res, R.drawable.pow);
        bitmapRip = BitmapFactory.decodeResource(res, R.drawable.rip_game);
        bitmapTemps = BitmapFactory.decodeResource(res, R.drawable.ic_timer);
        bitmapChat = BitmapFactory.decodeResource(res, R.drawable.chat);

        parametrerSonPerso();

        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);
        mFileX = 500;
        mFileY = 500;

        parametrerImagePerso(res);

        super.setOnTouchListener(this);
        removeCallbacks(animator);
        post(animator);

        spriteAnimation = new SpriteAnimation(
                bitmapChat
                , 0, 120	// initial position
                , 5, 13);	// FPS and number of frames in the animation
    }

    public GameCustomView(Context context) {
        super(context);
        init();
    }

    public GameCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void recupererPreferences() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        color = prefs.getString("pref_theme", "#FFA500");
        perso = Integer.parseInt(prefs.getString("pref_perso", "1"));
        prefVitesse = prefs.getInt("seekbar_vitesse", 25);
        son = Integer.parseInt(prefs.getString("pref_son", "1"));
    }

    private void parametrerImagePerso(Resources res) {
        switch (perso) {
            case 1:
                bitmapPerso = BitmapFactory.decodeResource(res, R.drawable.bender_ghost);
                break;
            case 2:
                bitmapPerso = BitmapFactory.decodeResource(res, R.drawable.blinky_pacman);
                break;
            case 3 :
                bitmapPerso = BitmapFactory.decodeResource(res, R.drawable.space_invaders_alien);
                break;
            case 4 :
                bitmapPerso = BitmapFactory.decodeResource(res, R.drawable.roi_boo);
                break;
        }
    }

    private void parametrerSonPerso() {
        switch (son) {
            case 1:
                mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.fantome);
                break;
            case 2:
                mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.yoshi);
                break;
            case 3 :
                mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.doh);
                break;
            case 4 :
                mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.nope);
                break;
            case 5 :
                mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.error_windows);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        String topScore = MainActivity._scoreDataBase.getTopScore();
        canvas.drawText("Score : " + String.valueOf(score), 50, 50, paint);
        canvas.drawText("BestScore : " + String.valueOf(topScore), 50, 120, paint);
        canvas.drawBitmap(bitmapTemps, screenWidth - 300, 0, null);
        canvas.drawText(String.valueOf(GameActivity.getSecs()), screenWidth - 160, 80, paint);
        if (GameActivity.isGame()) {

            //spriteAnimation.draw(canvas);

            if (isInvisible)
                canvas.drawBitmap(bitmapPerso, mFileX, mFileY, null);
            else {
                //si on est sur le thème halloween, on met le RIP
                if (color.equals("#EE7600"))
                    canvas.drawBitmap(bitmapRip, mFileX, mFileY, null);
                //sinon on met l'image POW
                else if (color.equals("#3f51b5"))
                    canvas.drawBitmap(bitmapPow, mFileX, mFileY, null);
            }
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!GameActivity.getPaused()) {
                    if ( x >= mFileX && x <= mFileX + IMAGE_SIZE && y >= mFileY
                            && y <= mFileY + IMAGE_SIZE) {

                        if (prefs.getBoolean("switch_sons", true))
                            mMediaPlayer.start();
                        if (prefs.getBoolean("switch_vibreur", true))
                            vibrator.vibrate(100);
                        setVisibility(View.VISIBLE);
                        score++;
                        invalidate();
                    }
                    else {
                        setVisibility(View.INVISIBLE);
                    }
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                setVisibility(View.INVISIBLE);
                return true;
            case MotionEvent.ACTION_UP:
                setVisibility(View.INVISIBLE);
            default:
                return false;
        }
    }

    //déplacement du personnage
    public void update() {
        if (!GameActivity.getPaused()){
            Random randomValue = new Random();

            mFileX = (float)randomValue.nextInt(screenWidth - IMAGE_SIZE);
            mFileY = (float)randomValue.nextInt(screenHeight - IMAGE_SIZE);

            //spriteAnimation.update();
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        screenWidth = getWidth();
        screenHeight = getHeight();
    }

    @Override
    public void setVisibility(int visibility) {
        isInvisible = visibility == View.INVISIBLE;
    }
}
