package com.badlogic.androidgames.glbasics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dell on 8/4/2016.
 */
public interface Renderer {
    public void onSurfaceCreated(GL10 gl, EGLConfig config);
    public void onSurfaceChanged(GL10 gl, int width, int height);
    public void onDrawFrame(GL10 gl);
}
