package com.badlogic.androidgames.framework.HANDLER.TOUCH;

/**
 * Created by dell on 8/1/2016.
 */
import java.util.List;
import android.view.View.OnTouchListener;
import com.badlogic.androidgames.framework.INPUT.Input.TouchEvent;
public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
    public List<TouchEvent> getTouchEvents();
}
