package com.badlogic.androidgames.framework.math;

/**
 * Created by dell on 8/5/2016.
 */
public class Circle {
    public final Vector2 center = new Vector2();
    public float radius;
    public Circle(float x, float y, float radius) {
        this.center.set(x,y);
        this.radius = radius;
    }
    public boolean overlapCircles(Circle c1, Circle c2){
        float distance =(float)c1.center.distSquared(c2.center);
        float radiusSum = (c1.radius + c2.radius);
        return distance <= radiusSum*radiusSum;
    }
}
