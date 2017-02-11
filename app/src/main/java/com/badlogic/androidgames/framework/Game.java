package com.badlogic.androidgames.framework;

import com.badlogic.androidgames.framework.AUDIO.Audio;
import com.badlogic.androidgames.framework.FILEIO.FileIO;
import com.badlogic.androidgames.framework.GRAPHICS.Graphics;
import com.badlogic.androidgames.framework.INPUT.Input;

/**
 * Created by dell on 7/30/2016.
 */
public interface Game {
    public Input getInput();
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(Screen screen);
    public Screen getCurrentScreen();
    public Screen getStartScreen();
}
