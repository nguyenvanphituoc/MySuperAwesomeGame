package com.badlogic.androidgames.framework.gamedev2d;

import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

/**
 * Created by dell on 8/5/2016.
 */
public class GameObject {
    public final Vector2 position;
    public final Rectangle bounds;
    public GameObject(float x, float y, float width, float height) {
        this.position = new Vector2(x,y);
        this.bounds = new Rectangle(x-width/2, y-height/2, width, height);
    }
}