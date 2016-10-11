package orlandini.jeu;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.Random;

/**
 * Created by Nicolas on 09/10/2016.
 */

public class GameCustomView extends View implements View.OnTouchListener {

    private final int ICON_SIZE = 70;

    private float mFileStartX;
    private float mFileStartY;
    private float mFileX;
    private float mFileY;
    private int screenWidth;
    private int screenHeight;

    private Bitmap bitmap;
    private Paint paint;
    private MediaPlayer mMediaPlayer;
    private Vibrator vibrator;
    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            long now = AnimationUtils.currentAnimationTimeMillis();
            update();
            invalidate();
            if(!isAtReset()){
                postDelayed(this,150);
            }
        }
    };

    public void init () {
        paint = new Paint();
        paint.setTextSize(50);
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.mini);
        mMediaPlayer = MediaPlayer.create(this.getContext(), R.raw.yoshi);
        vibrator = (Vibrator) this.getContext().getSystemService(Activity.VIBRATOR_SERVICE);
        mFileStartX = 100;
        mFileStartY = 100;
        mFileX = mFileStartX;
        mFileY = mFileStartY;

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
        //canvas.drawText(getResources().getString(R.string.text_auto), mFileX, mFileY, paint);
        canvas.drawBitmap(bitmap, mFileX, mFileY, null);
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
                mMediaPlayer.start();
                vibrator.vibrate(300);
            }
            return true;
        }
        if(action == MotionEvent.ACTION_MOVE)
        {
            return true;
        }
        return false;

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
        float value1 = (float)randomValue.nextInt(screenWidth - (int)mFileX);
        float value2 = (float)randomValue.nextInt(screenHeight);

        mFileX = value1;
        mFileY = value2;
    }

    public boolean isAtReset(){
        if(mFileX <= screenWidth || mFileY <= screenHeight)
            return false;
        else
            return true;
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        screenWidth = getWidth();
        screenHeight = getHeight();
    }
}
