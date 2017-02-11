package com.example.dell.mysuperawesomegame.jumper;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.example.dell.mysuperawesomegame.mrnom2d.MainMenuScreen;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by dell on 8/5/2016.
 */
public class SuperJumper extends GLGame {
    boolean firstTimeCreate = true;
    @Override
    public Screen getStartScreen() {
        return new com.example.dell.mysuperawesomegame.jumper.MainMenuScreen(this);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        if(firstTimeCreate) {
            Settings.load(getFileIO());
            Assets.load(this);
            firstTimeCreate = false;
        } else {
            Assets.reload();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if(Settings.soundEnabled)
            Assets.music.pause();
    }
}
