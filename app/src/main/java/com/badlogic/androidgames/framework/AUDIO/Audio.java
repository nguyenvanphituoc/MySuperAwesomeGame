package com.badlogic.androidgames.framework.AUDIO;

import com.badlogic.androidgames.framework.MUSIC.Music;
import com.badlogic.androidgames.framework.SOUND.Sound;

/**
 * Created by dell on 7/30/2016.
 */
public interface Audio {
    public Music newMusic(String filename);
    public Sound newSound(String filename);
}

