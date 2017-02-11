package com.badlogic.androidgames.framework.LAYOUT;

import java.io.IOException;
import java.io.InputStream;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BitmapTest extends Activity {
    class RenderView extends View {
        Bitmap bob565;
        Bitmap bob4444;
        Rect dst = new Rect();
        public RenderView(Context context) {
            super(context);
            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open("2dnom/bobrgb888.png");
                bob565 = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                Log.d("BitmapText",
                        "bobrgb888.png format: " + bob565.getConfig());
                inputStream = assetManager.open("2dnom/bobargb8888.png");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                bob4444 = BitmapFactory
                        .decodeStream(inputStream, null, options);
                inputStream.close();
                Log.d("BitmapText",
                        "bobargb8888.png format: " + bob4444.getConfig());
            } catch (IOException e) {
// silently ignored, bad coder monkey, baaad!
            } finally {
// we should really close our input streams here.
            }
        }
        protected void onDraw(Canvas canvas) {
          //  dst.set(50, 50, 350, 350);
            //canvas.drawBitmap(bob565, null, dst, null);
            //canvas.drawBitmap(bob4444, 100, 100, null);
            int originalWidth = bob4444.getWidth();
            int originalHeight = bob4444.getHeight();
            int size = originalWidth;
            if (originalHeight < originalWidth) {
                size = originalHeight;
            }
            // init a square bitmap and canvas which holds the bitmap
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.GRAY);
            paint.setStyle(Paint.Style.FILL);
            // draw circle
            canvas.drawCircle(size/2, size/2, size/2, paint);
            // set paint mode
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            // draw original bitmap
            if (size == originalHeight) {
                canvas.drawBitmap(bob4444, -(originalWidth/2 - size/2), 0, paint);
            } else {
                canvas.drawBitmap(bob4444, 0, -(originalHeight/2 - size/2), paint);
            }
            invalidate();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(new RenderView(this));
    }
}
