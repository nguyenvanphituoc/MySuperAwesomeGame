package com.badlogic.androidgames.framework.PIXMAP;
import com.badlogic.androidgames.framework.GRAPHICS.Graphics.PixmapFormat;
/**
 * Created by dell on 7/30/2016.
 */
public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}
