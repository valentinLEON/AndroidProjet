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
import android.view.animation.AnimationUtils;

import java.util.Random;

/**
 * Auteur : Nicolas Orlandini
 * Date de création : 09/10/2016
 * Dernière modification : 24/10/2016
 */

public class GameCustomView extends View implements View.OnTouchListener {

    private final int ICON_SIZE = 200;

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

    private Bitmap bitmapBender;
    private Bitmap bitmapPow;
    private Paint paint;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getContext());

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            update();
            invalidate();
            if(!isAtReset()){
                prefVitesse = prefs.getInt("seekbar_vitesse", 0);
                postDelayed(this,prefVitesse);

                //Toast.makeText(getContext(), String.valueOf(vitesse),Toast.LENGTH_LONG).show();
            }
        }
    };

    public void init () {
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.WHITE);
        Resources res = getResources();
        bitmapBender = BitmapFactory.decodeResource(res, R.drawable.bender_ghost);
        bitmapPow = BitmapFactory.decodeResource(res, R.drawable.pow);

        mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.yoshi);
        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);
        mFileX = 500;
        mFileY = 500;

        super.setOnTouchListener(this);
        removeCallbacks(animator);
        post(animator);
    }

    public GameCustomView(Context context) {
        super(context);
        init();
    }

    public GameCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText("Score : " + String.valueOf(score), 50, 50, paint);
        /* ajouter les minutes = String.valueOf(GameFragment.getMins()) + ":" + */
        canvas.drawText(String.valueOf(GameActivity.getSecs()), screenWidth - 200, 50, paint);
        canvas.drawBitmap(bitmapBender, mFileX, mFileY, null);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();

        if (action == MotionEvent.ACTION_DOWN)
        {
            if ( x >= mFileX && x <= mFileX + ICON_SIZE && y >= mFileY
                    && y <= mFileY + ICON_SIZE) {
                //canvasPow.drawBitmap(bitmapPow, mFileX, mFileY, null);
                if (prefs.getBoolean("switch_sons", true))
                    mMediaPlayer.start();
                if (prefs.getBoolean("switch_vibreur", true))
                    vibrator.vibrate(100);

                score++;
                invalidate();
            }
            return true;
        }
        return action == MotionEvent.ACTION_MOVE;

        /*switch (action) {
            case MotionEvent.ACTION_DOWN:
                if ( x >= mFileX && x <= mFileX + ICON_SIZE && y >= mFileY
                        && y <= mFileY + ICON_SIZE) {
                    mMediaPlayer.start();
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                vibrator.vibrate(500);
                return true;
            case MotionEvent.ACTION_UP:
                vibrator.cancel();
            default:
                return false;
        }*/
    }

    public void update() {
        Random randomValue = new Random();
        float value1 = (float)randomValue.nextInt(screenWidth - 50);
        float value2 = (float)randomValue.nextInt(screenHeight - 120);

        mFileX = value1;
        mFileY = value2;
    }

    public boolean isAtReset(){
        return !(mFileX <= screenWidth || mFileY <= screenHeight);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        screenWidth = getWidth();
        screenHeight = getHeight();
    }
}
