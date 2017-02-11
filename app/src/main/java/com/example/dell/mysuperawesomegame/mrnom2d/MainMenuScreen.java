package com.example.dell.mysuperawesomegame.mrnom2d;

import com.badlogic.androidgames.framework.GRAPHICS.Graphics;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.INPUT.Input;
import com.badlogic.androidgames.framework.INPUT.Input.*;
import com.badlogic.androidgames.framework.Screen;

import java.util.List;

/**
 * Created by dell on 8/2/2016.
 */
public class MainMenuScreen extends Screen {
    public MainMenuScreen(Game game){
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents =  game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_DOWN) {
                if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
                    Settings.soundEnabled = !Settings.soundEnabled;
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                }
                if(inBounds(event, 64, 220, 192, 42) ) {
                    game.setScreen(new GameScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 42, 192, 42) ) {
                    game.setScreen(new HighscoreScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 64, 220 + 84, 192, 42) ) {
                    game.setScreen(new HelpScreen(game));
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if(inBounds(event, 256, 416, 64, 64)){
                    if(Settings.soundEnabled)
                        Assets.click.play(1);
                    dispose();
                }
            }
        }
    }
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 &&
                event.y > y && event.y < y + height - 1)
            return true;
        else
            return false;
    }
    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.logo, 32, 20);
        g.drawPixmap(Assets.mainMenu, 64, 220);
        g.drawPixmap(Assets.buttons, 256,416, 0, 128, 64, 64);
        if(Settings.soundEnabled)
            g.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
        else
            g.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
    }
    @Override
    public void pause() {
        Settings.save(game.getFileIO());
    }
    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}
