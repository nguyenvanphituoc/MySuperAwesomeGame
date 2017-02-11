package com.badlogic.androidgames.framework.PIXMAP;

import android.graphics.Bitmap;

import com.badlogic.androidgames.framework.GRAPHICS.Graphics;

/**
 * Created by dell on 8/1/2016.
 */

public class AndroidPixmap implements Pixmap {
    public Bitmap bitmap;
    public Graphics.PixmapFormat format;
    public AndroidPixmap(Bitmap bitmap, Graphics.PixmapFormat format) {
        this.bitmap = bitmap;
    }
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }
    @Override
    public Graphics.PixmapFormat getFormat() {
        return format;
    }
    @Override
    public void dispose() {
        bitmap.recycle();
    }
}