package com.badlogic.androidgames.framework.gl;

import android.util.Log;

/**
 * Created by dell on 8/4/2016.
 */
public class FPSCounter {
    long startTime = System.nanoTime();
    int frames = 0;
    public void logFrame() {
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;
            startTime = System.nanoTime();
        }
    }
}
