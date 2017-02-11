package com.badlogic.androidgames.framework.gamedev2d;

import com.badlogic.androidgames.framework.gamedev2d.GameObject;
import com.badlogic.androidgames.framework.math.Vector2;

/**
 * Created by dell on 8/5/2016.
 */
public class DynamicGameObject extends GameObject {
    public final Vector2 velocity;
    public final Vector2 accel;
    public DynamicGameObject(float x, float y, float width, float height) {
        super(x, y, width, height);
        velocity = new Vector2();
        accel = new Vector2();
    }
}
