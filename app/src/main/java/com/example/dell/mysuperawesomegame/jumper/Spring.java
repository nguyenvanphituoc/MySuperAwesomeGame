package com.example.dell.mysuperawesomegame.jumper;

import com.badlogic.androidgames.framework.gamedev2d.GameObject;

/**
 * Created by dell on 8/5/2016.
 */
public class Spring extends GameObject {
    public static float SPRING_WIDTH = 0.3f;
    public static float SPRING_HEIGHT = 0.3f;
    public Spring(float x, float y) {
        super(x, y, SPRING_WIDTH, SPRING_HEIGHT);
    }
}
