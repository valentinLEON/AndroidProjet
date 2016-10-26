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
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Auteur : Nicolas Orlandini
 * Date de création : 26/10/2016
 * Dernière modification : 26/10/2016
 */

public class EasterEggCustomView extends View implements View.OnTouchListener {

    private final int ICON_SIZE = 350;

    private float mFileX;
    private float mFileY;
    private float mDmcX;
    private float mDmcY;
    private int screenWidth;
    private int screenHeight;

    public static int getScore() {
        return score;
    }

    //public static void setScore(int score) { GameCustomView.score = score; }

    private static int score = 0;
    private int prefVitesse = 500;

    boolean isInvisible = true;
    private boolean isMove = false;

    private Bitmap bitmapBender;
    private Bitmap bitmapDelorean;
    private Bitmap bitmapRip;
    private Paint paint;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            update();
            invalidate();
            if(!isAtReset()){
                prefVitesse = prefs.getInt("seekbar_vitesse", 0);
                postDelayed(this,prefVitesse);
            }
        }
    };

    private Runnable animDelorean = new Runnable() {
        @Override
        public void run() {
            invalidate();
            postDelayed(this,10);
        }
    };

    public void init () {
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        Resources res = getResources();
        bitmapBender = BitmapFactory.decodeResource(res, R.drawable.bender_ghost);
        bitmapDelorean = BitmapFactory.decodeResource(res, R.drawable.delorean1);
        bitmapRip = BitmapFactory.decodeResource(res, R.drawable.rip_game);

        mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.yoshi);
        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);
        mFileX = 500;
        mFileY = 500;
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
        canvas.drawText("Score : " + String.valueOf(score), 50, 50, paint);
        /* ajouter les minutes = String.valueOf(GameFragment.getMins()) + ":" + */
        canvas.drawText(String.valueOf(GameActivity.getSecs()), screenWidth - 200, 50, paint);
        canvas.drawBitmap(bitmapDelorean, mDmcX, mDmcY, null);
        if (isInvisible)
            canvas.drawBitmap(bitmapBender, mFileX, mFileY, null);
        if (!isInvisible)
            canvas.drawBitmap(bitmapRip, mFileX, mFileY, null);
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
                    mDmcX = x - ICON_SIZE;
                    mDmcY = y - 100;
                    if (mDmcX + ICON_SIZE >= mFileX - 300 && mDmcX <= mFileX + 100 && mDmcY + 100 >= mFileY
                            && mDmcY + 100 <= mFileY + 91) {
                        if (prefs.getBoolean("switch_sons", true))
                            mMediaPlayer.start();
                        if (prefs.getBoolean("switch_vibreur", true))
                            vibrator.vibrate(100);
                        setVisibility(View.VISIBLE);
                        score++;
                        invalidate();
                    }
                    else
                        setVisibility(View.INVISIBLE);
                    isMove = true;
                    return true;
                case MotionEvent.ACTION_UP:
                    isMove = false;
                default:
                    return false;
            }
        }
        else
            return false;
    }

    public void update() {
        Random randomValue = new Random();

        if (!GameActivity.getPaused()){
            mFileX = (float)randomValue.nextInt(screenWidth - 50);
            mFileY = (float)randomValue.nextInt(screenHeight - 120);
        }
    }

    public boolean isAtReset(){
        return !(mFileX <= screenWidth || mFileY <= screenHeight);
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
