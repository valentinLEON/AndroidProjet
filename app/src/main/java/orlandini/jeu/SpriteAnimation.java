package orlandini.jeu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * Created by Nicolas on 01/11/2016.
 */

public class SpriteAnimation {

    private static final String TAG = SpriteAnimation.class.getSimpleName();

    private Bitmap bitmap;		// the animation sequence
    private Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
    private Rect destRect;
    private int frameNr;		// number of frames in animation
    private int currentFrame;	// the current frame
    private int framePeriod;	// milliseconds between each frame (1000/fps)

    private int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;	// the height of the sprite

    private int x;				// the X coordinate of the object (top left of the image)
    private int y;				// the Y coordinate of the object (top left of the image)
    private int position = 0;

    public SpriteAnimation(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        currentFrame = 0;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        framePeriod = 1000 / fps;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // the update method for Elaine
    public void update() {
        /*if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            // increment the frame
            currentFrame++;
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;*/
        if (position < frameNr - 1)
            position++;
        else
            position = 0;
    }

    // the draw method which draws the corresponding frame
    public void draw(Canvas canvas) {

        int[] tabInt = {0, 186, 372, 558, 744, 930, 1116, 1302, 1488, 1674, 1860, 2046, 2232};

        sourceRect = new Rect(0, 0, spriteWidth * frameNr, spriteHeight);
        destRect = new Rect(tabInt[position], 0, spriteWidth, spriteHeight);
        Paint paint = new Paint();
        paint.setARGB(50, 0, 255, 0);
        canvas.drawBitmap(bitmap, sourceRect, destRect, paint);
        canvas.drawBitmap(bitmap, 100, 150, paint);
        //Toast.makeText(this, "position x : " + String.valueOf(tabInt[position]), Toast.LENGTH_LONG).show();

        /*Paint paint = new Paint();
        paint.setARGB(50, 0, 255, 0);
        canvas.drawRect(20 + (currentFrame * destRect.width()), 150, 20 + (currentFrame * destRect.width()) + destRect.width(), 150 + destRect.height(),  paint);*/
    }
}